package nl.wessel.juice.Service;

import nl.wessel.juice.DTO.Domain.CreateDomainDto;
import nl.wessel.juice.DTO.Publisher.CreatePublisherDto;
import nl.wessel.juice.DTO.Publisher.CreatedPublisherDto;
import nl.wessel.juice.Exception.BadRequest;
import nl.wessel.juice.Exception.UsernameNotFound;
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

    private final DomainRepository domainRepository;
    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(DomainRepository domainRepository, PublisherRepository publisherRepository) {
        this.domainRepository = domainRepository;
        this.publisherRepository = publisherRepository;
    }

    public Publisher toPublisher(CreatedPublisherDto createdPublisherDto) {
        Publisher publisher = new Publisher();
        publisher.setUsername(createdPublisherDto.getUsername());
        publisher.setPassword(createdPublisherDto.getPassword());
        return publisher;
    }

    public Publisher publisherMaker(CreatePublisherDto createPublisherDto) {
        Publisher publisher = new Publisher();
        publisher.setUsername(createPublisherDto.getUsername());
        publisher.setPassword(createPublisherDto.getPassword());
        return publisher;
    }

    public static CreatedPublisherDto publisherDtoMaker(Publisher publisher) {
        CreatedPublisherDto createdPublisherDto = new CreatedPublisherDto();
        createdPublisherDto.setUsername(publisher.getUsername());
        createdPublisherDto.setPassword(publisher.getPassword());
        List<Domain> domains = publisher.getDomains();
        List<Long> domainIDs = new ArrayList<>();
        if (domains != null) {
            for (Domain domain : domains) {
                domainIDs.add(domain.getDomainID());
            }
        }
        createdPublisherDto.setDomainIDs(domainIDs);
        return createdPublisherDto;
    }

    public List<CreatedPublisherDto> getPublishers() {
        List<CreatedPublisherDto> collection = new ArrayList<>();
        List<Publisher> list = publisherRepository.findAll();
        for (Publisher publisher : list) {
            collection.add(publisherDtoMaker(publisher));
        }
        return collection;
    }

    public CreatedPublisherDto getPublisher(String publisherName) {
        CreatedPublisherDto dto = new CreatedPublisherDto();
        var publisher = publisherRepository.findById(publisherName);
        if (publisher.isPresent()) {
            dto = publisherDtoMaker(publisher.get());
        } else {
            throw new UsernameNotFound(publisherName);
        }
        return dto;
    }

    public boolean publisherExists(String publisherName) {
        return publisherRepository.existsById(publisherName);
    }

    public String create(CreatePublisherDto createdPublisherDto) {
        Publisher publisher = publisherMaker(createdPublisherDto);
        publisherRepository.save(publisher);
        return publisherDtoMaker(publisher).getUsername();
    }

    public void delete(String publisherName) {
        publisherRepository.deleteById(publisherName);
    }

    public void update(String publisherName, CreatePublisherDto createPublisherDto) {
        if (!publisherRepository.existsById(publisherName)) throw new BadRequest();
        Publisher currentPublisher = publisherRepository.findById(publisherName).get();
        Publisher updatedPublisher = publisherMaker(createPublisherDto);
        updatedPublisher.setUsername(currentPublisher.getUsername());
        publisherRepository.save(updatedPublisher);
    }

    public Set<Authority> getAuthorities(String publisherName) {
        if (!publisherRepository.existsById(publisherName)) throw new UsernameNotFound(publisherName);
        Publisher publisher = publisherRepository.findById(publisherName).get();
        CreatedPublisherDto createdPublisherDto = publisherDtoMaker(publisher);
        return createdPublisherDto.getAuthorities();
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


    public CreatedPublisherDto newDomain(CreateDomainDto createDomainDto, String username) {

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
            return publisherDtoMaker(publisher);

        } else {

            throw new BadRequest("This Publisher does not show up in the Database. Are you sure you made it?");
        }
    }


}
