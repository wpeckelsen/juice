package nl.wessel.juice.Service;

import nl.wessel.juice.DTO.Publisher.PublicPublisherDto;
import nl.wessel.juice.DTO.Publisher.PublisherDto;
import nl.wessel.juice.Exception.BadRequestException;
import nl.wessel.juice.Exception.RecordNotFoundException;
import nl.wessel.juice.Exception.UsernameNotFoundException;
import nl.wessel.juice.Model.Authority;
import nl.wessel.juice.Model.Deal;
import nl.wessel.juice.Model.Domain;
import nl.wessel.juice.Model.Publisher;
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

    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public static PublisherDto fromPublisher(Publisher publisher) {
        PublisherDto publisherDto = new PublisherDto();
        publisherDto.username = publisher.getUsername();
        publisherDto.password = publisher.getPassword();
        publisherDto.setAuthorities(publisher.getAuthorities());

        List<Domain> domains = publisher.getDomains();
        List<Long> domainIDs = new ArrayList<>();
        if (domains != null) {
            for (Domain domain : domains) {
                domainIDs.add(domain.getDomainID());
            }
        }
        publisherDto.setDomainIDs(domainIDs);

        List<Deal> deals = publisher.getDeals();
        List<Long> dealIDs = new ArrayList<>();
        if (deals != null) {
            for (Deal deal : deals) {
                dealIDs.add(deal.getDealID());
            }
        }
        publisherDto.setDealIDs(dealIDs);
        return publisherDto;
    }

    public Publisher toPublisher(PublisherDto publisherDto) {
        Publisher publisher = new Publisher();
        publisher.setUsername(publisherDto.getUsername());
        publisher.setPassword(publisherDto.getPassword());
        return publisher;
    }

    public PublisherDto getPublisher(String publisherName) {
        PublisherDto dto;
        var publisher = publisherRepository.findById(publisherName);
        if (publisher.isPresent()) {
            dto = fromPublisher(publisher.get());
        } else {
            throw new UsernameNotFoundException(publisherName);
        }
        return dto;
    }

    public List<String> getPublishers() {
        List<Publisher> publishersList = publisherRepository.findAll();

        if (publishersList.isEmpty()) {
            Publisher publisher = new Publisher();
            throw new RecordNotFoundException(publisher);
        } else {
            List<String> publisherIDs = new ArrayList<>();
            for (Publisher publisher : publishersList) {
                publisherIDs.add(fromPublisher(publisher).username);
            }
            return publisherIDs;
        }
    }

    public PublicPublisherDto getPublicPublisher(String publisherName) {
        Optional<Publisher> publisher = publisherRepository.findById(publisherName);
        if (publisher.isPresent()) {
            PublisherDto dto = fromPublisher(publisher.get());

            PublicPublisherDto publicPublisherDto = new PublicPublisherDto();
            publicPublisherDto.setDomainIDs(dto.getDomainIDs());
            publicPublisherDto.setUsername(dto.getUsername());
            publicPublisherDto.setAuthorities(dto.getAuthorities());
            publicPublisherDto.setDealIDs(dto.getDealIDs());
            return publicPublisherDto;
        } else {
            throw new UsernameNotFoundException(publisherName);
        }
    }

    public String newPublisher(PublisherDto publisherDto) {
        Publisher publisher = toPublisher(publisherDto);
        publisherRepository.save(publisher);
        return publisher.getUsername();
    }


    public void delete(String publisherName) {
        publisherRepository.deleteById(publisherName);
    }

    public void update(String publisherName, PublisherDto publisherDto) {
        if (!publisherRepository.existsById(publisherName))
            throw new BadRequestException("This Publisher does not show up in the Database. Are you sure you made it?");
        var optionalPublisher = publisherRepository.findById(publisherName);

        if (optionalPublisher.isPresent()) {
            Publisher publisher = optionalPublisher.get();
            publisher.setUsername(publisherDto.getUsername());
            publisher.setUsername(publisherDto.getUsername());
            publisherRepository.save(publisher);
        }
    }

    public void addAuthority(String publisherName, String authority) {
        if (!publisherRepository.existsById(publisherName)) throw new UsernameNotFoundException(publisherName);

        Optional<Publisher> optionalPublisher = publisherRepository.findById(publisherName);
        if (optionalPublisher.isPresent()) {
            Publisher publisher = optionalPublisher.get();
            publisher.addAuthority(new Authority(publisherName, authority));
            publisherRepository.save(publisher);
        }
    }


}
