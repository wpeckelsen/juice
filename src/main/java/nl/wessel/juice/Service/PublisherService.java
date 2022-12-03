package nl.wessel.juice.Service;

import nl.wessel.juice.DTO.Domain.CreateDomain;
import nl.wessel.juice.DTO.Domain.CreatedDomain;
import nl.wessel.juice.DTO.Publisher.PublisherDto;
import nl.wessel.juice.Exception.BadRequest;
import nl.wessel.juice.Exception.UsernameNotFound;
import nl.wessel.juice.Security.Utils.RandomStringGenerator;
import nl.wessel.juice.Model.Authority;
import nl.wessel.juice.Model.Domain;
import nl.wessel.juice.Model.Publisher;
import nl.wessel.juice.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PublisherService {

    private final CustomerRepository customerRepository;
    private final BidRepository bidRepository;
    private final DomainRepository domainRepository;
    private final DealRepository dealRepository;
    private final PublisherRepository publisherRepository;
    private final DomainService domainService;

    @Autowired
    public PublisherService(CustomerRepository customerRepository, BidRepository bidRepository, DomainRepository domainRepository, DealRepository dealRepository, PublisherRepository publisherRepository, DomainService domainService) {
        this.customerRepository = customerRepository;
        this.bidRepository = bidRepository;
        this.domainRepository = domainRepository;
        this.dealRepository = dealRepository;
        this.publisherRepository = publisherRepository;
        this.domainService = domainService;
    }


    public List<PublisherDto> getPublishers() {
        List<PublisherDto> collection = new ArrayList<>();
        List<Publisher> list = publisherRepository.findAll();
        for (Publisher publisher : list) {
            collection.add(fromPublisher(publisher));
        }
        return collection;
    }

    public PublisherDto getPublisher(String publisherName) {
        PublisherDto dto = new PublisherDto();
        var publisher = publisherRepository.findById(publisherName);
        if (publisher.isPresent()) {
            dto = fromPublisher(publisher.get());
        } else {
            throw new UsernameNotFound(publisherName);
        }
        return dto;
    }

    public boolean publisherExists(String publisherName) {
        return publisherRepository.existsById(publisherName);
    }

    public String createPublisher(PublisherDto publisherDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        publisherDto.setApikey(randomString);
        Publisher newPublisher = publisherRepository.save(toPublisher(publisherDto));
        return newPublisher.getUsername();
    }

    public void deletePublisher(String publisherName) {
        publisherRepository.deleteById(publisherName);
    }

    public void updatePublisher(String publisherName, PublisherDto newPublisher) {
        if (!publisherRepository.existsById(publisherName)) throw new BadRequest();
        Publisher publisher = publisherRepository.findById(publisherName).get();
        publisher.setUsername(newPublisher.getUsername());
        publisher.setPassword(newPublisher.getPassword());
        publisherRepository.save(publisher);
    }

    public Set<Authority> getAuthorities(String publisherName) {
        if (!publisherRepository.existsById(publisherName)) throw new UsernameNotFound(publisherName);
        Publisher publisher = publisherRepository.findById(publisherName).get();
        PublisherDto PublisherDto = fromPublisher(publisher);
        return PublisherDto.getAuthorities();
    }

    public void addAuthority(String publisherName, String authority) {

        if (!publisherRepository.existsById(publisherName)) throw new UsernameNotFound(publisherName);
        Publisher publisher = publisherRepository.findById(publisherName).get();
        publisher.addAuthority(new Authority(publisherName, authority));
        publisherRepository.save(publisher);
    }

    public void removeAuthority(String publisherName, String authority) {
        if (!publisherRepository.existsById(publisherName)) throw new UsernameNotFound(publisherName);
        Publisher publisher = publisherRepository.findById(publisherName).get();
        Authority authorityToRemove = publisher.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        publisher.removeAuthority(authorityToRemove);
        publisherRepository.save(publisher);
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

        var optCustom = publisherRepository.findById(username);


        if (optCustom.isPresent()) {
            Publisher publisher = optCustom.get();
            Domain newDomain = DomainService.domainMaker(createDomain);

            List<Domain> currentDomains = publisher.getDomains();
            currentDomains.add(newDomain);

            for (Domain domain : currentDomains) {
                domain.setPublisher(publisher);
                domainRepository.save(domain);
            }

            publisher.setDomains(currentDomains);
            publisherRepository.save(publisher);
            return fromPublisher(publisher);

        } else {

            throw new BadRequest("This Publisher does not show up in the Database. Are you sure you made it?");
        }
    }


}
