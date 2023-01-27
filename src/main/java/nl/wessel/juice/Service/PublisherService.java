package nl.wessel.juice.Service;

import nl.wessel.juice.DTO.Domain.CreateDomainDto;
import nl.wessel.juice.DTO.Domain.CreatedDomainDto;
import nl.wessel.juice.DTO.Publisher.CreatePublisherDto;
import nl.wessel.juice.DTO.Publisher.PublisherDto;
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
import java.util.Optional;

@Service
@Transactional
public class PublisherService {

    private final DomainRepository domainRepository;
    private final PublisherRepository publisherRepository;
//    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PublisherService(DomainRepository domainRepository, PublisherRepository publisherRepository/*, @Lazy PasswordEncoder passwordEncoder*/) {
        this.domainRepository = domainRepository;
        this.publisherRepository = publisherRepository;
//        this.passwordEncoder = passwordEncoder;
    }

    public Publisher publisherMaker(CreatePublisherDto createPublisherDto) {
//        String encodedPassword = passwordEncoder.encode(createPublisherDto.getPassword());


        Publisher publisher = new Publisher();
        publisher.setUsername(createPublisherDto.getUsername());

        publisher.setPassword(createPublisherDto.getPassword());
//        publisher.setPassword(encodedPassword);
        return publisher;
    }

    public static PublisherDto publisherDtoMaker(Publisher publisher) {
        PublisherDto publisherDto = new PublisherDto();
        publisherDto.setUsername(publisher.getUsername());
        publisherDto.setPassword(publisher.getPassword());
        List<Domain> domains = publisher.getDomains();
        List<Long> domainIDs = new ArrayList<>();
        if (domains != null) {
            for (Domain domain : domains) {
                domainIDs.add(domain.getDomainID());
            }
        }
        publisherDto.setDomainIDs(domainIDs);
        return publisherDto;
    }

    public List<PublisherDto> getPublishers() {
        List<PublisherDto> collection = new ArrayList<>();
        List<Publisher> list = publisherRepository.findAll();
        for (Publisher publisher : list) {
            collection.add(publisherDtoMaker(publisher));
        }
        return collection;
    }

    public PublisherDto getPublisher(String publisherName) {
        PublisherDto dto;
        var publisher = publisherRepository.findById(publisherName);
        if (publisher.isPresent()) {
            dto = publisherDtoMaker(publisher.get());
        } else {
            throw new UsernameNotFound(publisherName);
        }
        return dto;
    }

//    public boolean publisherExists(String publisherName) {
//        return publisherRepository.existsById(publisherName);
//    }

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
        var optionalPublisher = publisherRepository.findById(publisherName);

        if (optionalPublisher.isPresent()) {
            Publisher currentPublisher = optionalPublisher.get();
            Publisher updatedPublisher = publisherMaker(createPublisherDto);
            updatedPublisher.setUsername(currentPublisher.getUsername());
            publisherRepository.save(updatedPublisher);
        }
    }

    public void addAuthority(String publisherName, String authority) {

        if (!publisherRepository.existsById(publisherName)) throw new UsernameNotFound(publisherName);

        Optional<Publisher> optionalPublisher = publisherRepository.findById(publisherName);
        if(optionalPublisher.isPresent()){
            Publisher publisher = optionalPublisher.get();
            publisher.addAuthority(new Authority(publisherName, authority));
            publisherRepository.save(publisher);
        }
    }

//            if (optionalPublisher.isPresent()) {
//            Publisher currentPublisher = optionalPublisher.get();
//            Publisher updatedPublisher = publisherMaker(createPublisherDto);
//            updatedPublisher.setUsername(currentPublisher.getUsername());
//            publisherRepository.save(updatedPublisher);
//        }


    //    public Set<Authority> getAuthorities(String publisherName) {
//        if (!publisherRepository.existsById(publisherName)) throw new UsernameNotFound(publisherName);
//        Publisher publisher = publisherRepository.findById(publisherName).get();
//        PublisherDto createdPublisherDto = publisherDtoMaker(publisher);
//        return createdPublisherDto.getAuthorities();
//    }

//    public void removeAuthority(String publisherName, String authority) {
//        if (!publisherRepository.existsById(publisherName)) throw new UsernameNotFound(publisherName);
//        Publisher publisher = publisherRepository.findById(publisherName).get();
//        Authority authorityToRemove = publisher.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
//        publisher.removeAuthority(authorityToRemove);
//        publisherRepository.save(publisher);
//    }


    public CreatedDomainDto newDomain(CreateDomainDto createDomainDto, String username) {
        Optional<Publisher> foundPublisher = publisherRepository.findById(username);

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
            return DomainService.domainDtoMaker(newDomain);
        } else {
            throw new BadRequest("This Publisher does not show up in the Database. Are you sure you made it?");
        }
    }


}
