package nl.wessel.juice.B.BusinessLogic.Service;


import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreateDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreatedDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Publisher.PublisherDto;
import nl.wessel.juice.B.BusinessLogic.Exception.BadRequest;
import nl.wessel.juice.B.BusinessLogic.Exception.UsernameNotFound;
import nl.wessel.juice.B.BusinessLogic.Model.*;
import nl.wessel.juice.B.BusinessLogic.Security.Utils.RandomStringGenerator;
import nl.wessel.juice.C.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static nl.wessel.juice.B.BusinessLogic.Service.DomainService.domainMaker;

@Service
@Transactional
public class PublisherService {

    private final CustomerRepo customerRepo;
    private final BidRepo bidRepo;
    private final DomainRepo domainRepo;
    private final DealRepo dealRepo;
    private final PublisherRepo publisherRepo;
    private final DomainService domainService;

    @Autowired
    public PublisherService(CustomerRepo customerRepo, BidRepo bidRepo, DomainRepo domainRepo, DealRepo dealRepo, PublisherRepo publisherRepo, DomainService domainService) {
        this.customerRepo = customerRepo;
        this.bidRepo = bidRepo;
        this.domainRepo = domainRepo;
        this.dealRepo = dealRepo;
        this.publisherRepo = publisherRepo;
        this.domainService = domainService;
    }


    public List<PublisherDto> getPublishers() {
        List<PublisherDto> collection = new ArrayList<>();
        List<Publisher> list = publisherRepo.findAll();
        for (Publisher publisher : list) {
            collection.add(fromPublisher(publisher));
        }
        return collection;
    }

    public PublisherDto getPublisher(String publisherName) {
        PublisherDto dto = new PublisherDto();
        var publisher = publisherRepo.findById(publisherName);
        if (publisher.isPresent()) {
            dto = fromPublisher(publisher.get());
        } else {
            throw new UsernameNotFound(publisherName);
        }
        return dto;
    }

    public boolean publisherExists(String publisherName) {
        return publisherRepo.existsById(publisherName);
    }

    public String createPublisher(PublisherDto publisherDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        publisherDto.setApikey(randomString);
        Publisher newPublisher = publisherRepo.save(toPublisher(publisherDto));
        return newPublisher.getUsername();
    }

    public void deletePublisher(String publisherName) {
        publisherRepo.deleteById(publisherName);
    }

    public void updatePublisher(String publisherName, PublisherDto newPublisher) {
        if (!publisherRepo.existsById(publisherName)) throw new BadRequest();
        Publisher publisher = publisherRepo.findById(publisherName).get();
        publisher.setUsername(newPublisher.getUsername());
        publisher.setPassword(newPublisher.getPassword());
        publisherRepo.save(publisher);
    }

    public Set<Authority> getAuthorities(String publisherName) {
        if (!publisherRepo.existsById(publisherName)) throw new UsernameNotFound(publisherName);
        Publisher publisher = publisherRepo.findById(publisherName).get();
        PublisherDto PublisherDto = fromPublisher(publisher);
        return PublisherDto.getAuthorities();
    }

    public void addAuthority(String publisherName, String authority) {

        if (!publisherRepo.existsById(publisherName)) throw new UsernameNotFound(publisherName);
        Publisher publisher = publisherRepo.findById(publisherName).get();
        publisher.addAuthority(new Authority(publisherName, authority));
        publisherRepo.save(publisher);
    }

    public void removeAuthority(String publisherName, String authority) {
        if (!publisherRepo.existsById(publisherName)) throw new UsernameNotFound(publisherName);
        Publisher publisher = publisherRepo.findById(publisherName).get();
        Authority authorityToRemove = publisher.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        publisher.removeAuthority(authorityToRemove);
        publisherRepo.save(publisher);
    }

    public static PublisherDto fromPublisher(Publisher publisher) {

        var dto = new PublisherDto();
        dto.username = publisher.getUsername();
        dto.password = publisher.getPassword();
        dto.enabled = publisher.isEnabled();
        dto.apikey = publisher.getApikey();
        dto.email = publisher.getEmail();
        dto.authorities = publisher.getAuthorities();


        List<Domain> domains = publisher.getDomains();
        List<CreatedDomain> createdDomains = new ArrayList<>();

        if (domains != null) {
            for (Domain domain : domains) {
                CreatedDomain createdDomain = DomainService.domainDtoMaker(domain);
                createdDomains.add(createdDomain);
            }
        }


        dto.setDomains(createdDomains);
        return dto;
    }

    public Publisher toPublisher(PublisherDto publisherDto) {


        var publisher = new Publisher();
        publisher.setUsername(publisherDto.getUsername());
        publisher.setPassword(publisherDto.getPassword());
        publisher.setEnabled(publisherDto.getEnabled());
        publisher.setApikey(publisherDto.getApikey());
        publisher.setEmail(publisherDto.getEmail());

        return publisher;
    }



//    assigns a domain to a publisher
    public PublisherDto newDomain(CreateDomain createDomain, String username) {

        var optCustom = publisherRepo.findById(username);


        if (optCustom.isPresent()) {
            Publisher publisher = optCustom.get();
            Domain newDomain = domainMaker(createDomain);

            List<Domain> currentDomains = publisher.getDomains();
            currentDomains.add(newDomain);

            for (Domain domain : currentDomains) {
                domain.setPublisher(publisher);
                domainRepo.save(domain);
            }

            publisher.setDomains(currentDomains);
            publisherRepo.save(publisher);
            return fromPublisher(publisher);

        } else {

            throw new BadRequest("This Publisher does not show up in the Database. Are you sure you made it?");
        }
    }


}
