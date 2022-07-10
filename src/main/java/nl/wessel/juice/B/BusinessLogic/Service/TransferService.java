package nl.wessel.juice.B.BusinessLogic.Service;


import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreatedBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Customer.CustomerDto;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreateDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreatedDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreateDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreatedDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Publisher.CreatePublisher;
import nl.wessel.juice.B.BusinessLogic.DTO.Publisher.CreatedPublisher;
import nl.wessel.juice.B.BusinessLogic.Model.Deal;
import nl.wessel.juice.B.BusinessLogic.Model.Domain;
import nl.wessel.juice.B.BusinessLogic.Model.Publisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransferService {


    public static Deal dealMaker(CreateDeal createDeal) {
        Deal deal = new Deal();
        deal.setDeadline(createDeal.getDeadline());
        deal.setPaymentType(createDeal.getPaymentType());
        deal.setTerms(createDeal.getTerms());
        deal.setPrice(createDeal.getPrice());
        return deal;
    }

    public static CreatedDeal dealDtoMaker(Deal deal) {
        CreatedDeal createdDeal = new CreatedDeal();
        createdDeal.setDealID(deal.getDealID());
        createdDeal.setDeadline(deal.getDeadline());
        createdDeal.setPrice(deal.getPrice());
        createdDeal.setPaymentType(deal.getPaymentType());
        createdDeal.setTerms(deal.getTerms());

        var domain = deal.getDomain();
        var bid = deal.getBid();
        var customer = deal.getCustomer();
        var publisher = deal.getPublisher();

        if (domain != null
                && bid != null
                && customer != null
                && publisher != null
        ) {
            CreatedDomain createdDomain = domainDtoMaker(domain);
            createdDeal.setDomain(createdDomain);

            CreatedBid createdBid = BidService.bidDtoMaker(bid);
            createdDeal.setBid(createdBid);

            CustomerDto customerDto = CustomerService.fromCustomer(customer);
            createdDeal.setCustomer(customerDto);

            CreatedPublisher createdPublisher = publisherDtoMaker(publisher);
            createdDeal.setPublisher(createdPublisher);
        }


        return createdDeal;
    }

    public static CreatedPublisher publisherDtoMaker(Publisher publisher) {
        CreatedPublisher createdPublisher = new CreatedPublisher();
        createdPublisher.setPublisherID(publisher.getPublisherID());
        createdPublisher.setName(publisher.getName());
        createdPublisher.setCountry(publisher.getCountry());
        createdPublisher.setNiche(publisher.getNiche());


        List<Domain> domains = publisher.getDomains();
        List<CreatedDomain> createdDomains = new ArrayList<>();

        if (domains != null) {
            for (Domain domain : domains) {
                CreatedDomain createdDomain = domainDtoMaker(domain);
                createdDomains.add(createdDomain);
            }
        }

        createdPublisher.setDomains(createdDomains);


        return createdPublisher;
    }

    public static Publisher publisherMaker(CreatePublisher createPublisher) {
        Publisher publisher = new Publisher();
        publisher.setName(createPublisher.getName());
        publisher.setCountry(createPublisher.getCountry());
        publisher.setNiche(createPublisher.getNiche());
        return publisher;
    }

    public static CreatedDomain domainDtoMaker(Domain domain) {
        CreatedDomain createdDomain = new CreatedDomain();
        createdDomain.setDomainID(domain.getDomainID());
        createdDomain.setName(domain.getName());
        createdDomain.setTLD(domain.getTLD());
        createdDomain.setCategory(domain.getCategory());
        createdDomain.setPrice(domain.getPrice());
        return createdDomain;
    }

    public static Domain domainMaker(CreateDomain createDomain) {
        Domain domain = new Domain();
        domain.setName(createDomain.getName());
        domain.setTLD(createDomain.getTLD());
        domain.setCategory(createDomain.getCategory());
        domain.setPrice(createDomain.getPrice());
        return domain;
    }
}
