package nl.wessel.juice.Service;

import nl.wessel.juice.DTO.Domain.CreateDomainDto;
import nl.wessel.juice.DTO.Domain.CreatedDomainDto;
import nl.wessel.juice.Exception.BadRequestException;
import nl.wessel.juice.Exception.ForbiddenException;
import nl.wessel.juice.Exception.RecordNotFoundException;
import nl.wessel.juice.Model.Domain;
import nl.wessel.juice.Model.Publisher;
import nl.wessel.juice.Repository.DomainRepository;
import nl.wessel.juice.Repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DomainService {

    private final DomainRepository domainRepository;
    private final PublisherRepository publisherRepository;
    private final PublisherService publisherService;

    @Autowired
    public DomainService(DomainRepository domainRepository, PublisherRepository publisherRepository, PublisherService publisherService) {
        this.domainRepository = domainRepository;
        this.publisherRepository = publisherRepository;
        this.publisherService = publisherService;
    }

    public static Domain domainMaker(CreateDomainDto createDomainDto) {
        Domain domain = new Domain();
        domain.setName(createDomainDto.getName());
        domain.setTLD(createDomainDto.getTLD());
        domain.setCategory(createDomainDto.getCategory());
        domain.setPrice(createDomainDto.getPrice());
        return domain;
    }

    public static CreatedDomainDto domainDtoMaker(Domain domain) {
        CreatedDomainDto createdDomainDTO = new CreatedDomainDto();
        createdDomainDTO.setDomainID(domain.getDomainID());
        createdDomainDTO.setName(domain.getName());
        createdDomainDTO.setTLD(domain.getTLD());
        createdDomainDTO.setCategory(domain.getCategory());
        createdDomainDTO.setPrice(domain.getPrice());
        createdDomainDTO.setPrincipal(domain.getPrincipal());
        return createdDomainDTO;
    }


    public List<Long> getList() {
        List<Domain> domainList = domainRepository.findAll();
        if (domainList.isEmpty()) {
            Domain domain = new Domain();
            throw new RecordNotFoundException(domain);
        } else {
            List<Long> domainIDs = new ArrayList<>();

            for (Domain domain : domainList) {
                CreatedDomainDto createdDomainDTO = domainDtoMaker(domain);
                domainIDs.add(createdDomainDTO.getDomainID());
            }
            return domainIDs;
        }
    }

    public List<Domain> getSimilarTLDs(String TLD) {
        List<Domain> domains = domainRepository.findAll();

        if (domains.isEmpty()) {
            Domain domain = new Domain();
            throw new RecordNotFoundException(domain);
        } else {
            List<Domain> TLDs = new ArrayList<>();

            for (Domain domain : domains) {
                CreatedDomainDto createdDomainDTO = domainDtoMaker(domain);
                if (createdDomainDTO.getTLD().matches(TLD)) {
                    TLDs.add(domain);
                }
            }
            return TLDs;
        }
    }


    public CreatedDomainDto getByID(Long idDomain) {
        if (domainRepository.findById(idDomain).isPresent()) {
            Domain domain = domainRepository.findById(idDomain).get();
            return domainDtoMaker(domain);
        } else {
            Domain domain = new Domain();
            throw new RecordNotFoundException(domain);
        }
    }

    public String domainPrincipal(Long domainID) {
        Domain domain = domainRepository.findById(domainID).get();
        CreatedDomainDto createdDomainDto = domainDtoMaker(domain);
        return createdDomainDto.getPrincipal();
    }


    public CreatedDomainDto updateDomain(Long domainID, CreateDomainDto createDomainDto) {
//        this string gets the name of the principal. Meaning: it gets the username of the
//        current user (the principal) that's logged in.
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        String domainPrincipal = domainPrincipal(domainID);

        if (domainRepository.findById(domainID).isPresent()) {
            Domain domain = domainRepository.findById(domainID).get();

            if (currentPrincipalName.equalsIgnoreCase(domainPrincipal)) {
                Domain domain1 = domainMaker(createDomainDto);
                domain1.setDomainID(domain.getDomainID());
                domainRepository.save(domain1);
                return domainDtoMaker(domain1);
            } else {
//                if another user tries to update, it will return an exception because the principal name doesn't match
                throw new ForbiddenException();
            }

        } else {
            throw new RecordNotFoundException(new Domain());
        }
    }


    public CreatedDomainDto newDomain(CreateDomainDto createDomainDto) {
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        var foundPublisher = publisherRepository.findById(currentPrincipalName);

        if (foundPublisher.isPresent()) {
            Publisher publisher = foundPublisher.get();
            Domain newDomain = domainMaker(createDomainDto);
            List<Domain> currentDomains = publisher.getDomains();
            int size = currentDomains.size();

            if(size >= 501){
                throw new BadRequestException("You have reached your limit of 500 Domains.");
            } else{
                currentDomains.add(newDomain);
            }




            for (Domain domain : currentDomains) {
                domain.setPublisher(publisher);
                domain.setPrincipal(currentPrincipalName);
                domainRepository.save(domain);
            }
            publisher.setDomains(currentDomains);
            publisherRepository.save(publisher);
            return domainDtoMaker(newDomain);
        } else {
            throw new BadRequestException("This Publisher does not show up in the Database. Are you sure you made it?");
        }
    }


    public void deleteById(Long domainID) {
        domainRepository.deleteById(domainID);
    }

}
