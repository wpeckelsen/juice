package nl.wessel.juice.Service;

import nl.wessel.juice.DTO.Domain.CreateDomainDto;
import nl.wessel.juice.DTO.Publisher.CreatePublisherDto;
import nl.wessel.juice.DTO.Publisher.CreatedPublisherDto;
import nl.wessel.juice.DTO.Publisher.PublicPublisherDto;
import nl.wessel.juice.Exception.BadRequest;
import nl.wessel.juice.Exception.RecordNotFound;
import nl.wessel.juice.Exception.UsernameNotFound;
import nl.wessel.juice.Model.Authority;
import nl.wessel.juice.Model.Deal;
import nl.wessel.juice.Model.Domain;
import nl.wessel.juice.Model.Publisher;
import nl.wessel.juice.Repository.DomainRepository;
import nl.wessel.juice.Repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PublisherService {

    private final DomainRepository domainRepository;
    private final PublisherRepository publisherRepository;


    @Autowired
    public PublisherService(DomainRepository domainRepository, PublisherRepository publisherRepository) {
        this.domainRepository = domainRepository;
        this.publisherRepository = publisherRepository;
    }

    public static CreatedPublisherDto fromPublisher(Publisher publisher) {
        CreatedPublisherDto createdPublisherDto = new CreatedPublisherDto();
        createdPublisherDto.username = publisher.getUsername();
        createdPublisherDto.password = publisher.getPassword();
        createdPublisherDto.setAuthorities(publisher.getAuthorities());

        List<Domain> domains = publisher.getDomains();
        List<Long> domainIDs = new ArrayList<>();
        if (domains != null) {
            for (Domain domain : domains) {
                domainIDs.add(domain.getDomainID());
            }
        }
        createdPublisherDto.setDomainIDs(domainIDs);

        List<Deal> deals = publisher.getDeals();
        List<Long> dealIDs = new ArrayList<>();
        if (deals != null) {
            for (Deal deal : deals) {
                dealIDs.add(deal.getDealID());
            }
        }
        createdPublisherDto.setDealIDs(dealIDs);
        return createdPublisherDto;
    }

    public Publisher toPublisher(CreatedPublisherDto createdPublisherDto) {
        Publisher publisher = new Publisher();
        publisher.setUsername(createdPublisherDto.getUsername());
        publisher.setPassword(createdPublisherDto.getPassword());
        return publisher;
    }

    public CreatedPublisherDto getPublisher(String publisherName) {
        CreatedPublisherDto dto;
        var publisher = publisherRepository.findById(publisherName);
        if (publisher.isPresent()) {
            dto = fromPublisher(publisher.get());
        } else {
            throw new UsernameNotFound(publisherName);
        }
        return dto;
    }

    public List<String> getPublishers() {
        List<Publisher> publishersList = publisherRepository.findAll();

        if (publishersList.isEmpty()) {
            Publisher publisher = new Publisher();
            throw new RecordNotFound(publisher);
        } else {
            List<String> publisherIDs = new ArrayList<>();
            for (Publisher publisher : publishersList) {
                publisherIDs.add(fromPublisher(publisher).username);
            }
            return publisherIDs;
        }
    }

    public PublicPublisherDto getPublicPublisher(String publisherName){
        Optional<Publisher> publisher = publisherRepository.findById(publisherName);
        if(publisher.isPresent()){
            CreatedPublisherDto dto = fromPublisher(publisher.get());

            PublicPublisherDto publicPublisherDto = new PublicPublisherDto();
            publicPublisherDto.setDomainIDs(dto.getDomainIDs());
            publicPublisherDto.setUsername(dto.getUsername());
            publicPublisherDto.setAuthorities(dto.getAuthorities());
            publicPublisherDto.setDealIDs(dto.getDealIDs());
            return publicPublisherDto;
        } else
        {
            throw new UsernameNotFound(publisherName);
        }
    }

    public String newPublisher(CreatedPublisherDto createdPublisherDto) {
        Publisher publisher = toPublisher(createdPublisherDto);
        publisherRepository.save(publisher);
        return publisher.getUsername();
    }

    public Long newDomain(CreateDomainDto createDomainDto, String username) {
        var foundPublisher = publisherRepository.findById(username);

        if (foundPublisher.isPresent()) {
            Publisher publisher = foundPublisher.get();
            Domain newDomain = DomainService.domainMaker(createDomainDto);
            List<Domain> currentDomains = publisher.getDomains();
            currentDomains.add(newDomain);

            for (Domain domain : currentDomains) {
                domain.setPublisher(publisher);
                domainRepository.save(domain);
            }
            publisher.setDomains(currentDomains);
            publisherRepository.save(publisher);
            return DomainService.domainDtoMaker(newDomain).getDomainID();
        } else {
            throw new BadRequest("This Publisher does not show up in the Database. Are you sure you made it?");
        }
    }

    public void delete(String publisherName) {
        publisherRepository.deleteById(publisherName);
    }

    public void update(String publisherName, CreatedPublisherDto createdPublisherDto) {
        if (!publisherRepository.existsById(publisherName)) throw new BadRequest();
        var optionalPublisher = publisherRepository.findById(publisherName);

        if (optionalPublisher.isPresent()) {
            Publisher publisher = optionalPublisher.get();
            publisher.setUsername(createdPublisherDto.getUsername());
            publisher.setUsername(createdPublisherDto.getUsername());
            publisherRepository.save(publisher);
        }
    }

    public void addAuthority(String publisherName, String authority) {

        if (!publisherRepository.existsById(publisherName)) throw new UsernameNotFound(publisherName);

        Optional<Publisher> optionalPublisher = publisherRepository.findById(publisherName);
        if (optionalPublisher.isPresent()) {
            Publisher publisher = optionalPublisher.get();
            publisher.addAuthority(new Authority(publisherName, authority));
            publisherRepository.save(publisher);
        }
    }


}
