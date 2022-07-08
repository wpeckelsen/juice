package nl.wessel.juice.B.BusinessLogic.Service;


import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreatedBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Customer.CustomerDto;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreateDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Deal.CreatedDeal;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreatedDomain;
import nl.wessel.juice.B.BusinessLogic.Model.Deal;
import org.springframework.stereotype.Service;

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

        if (domain != null
                && bid != null
                && customer != null
        ) {
            CreatedDomain createdDomain = DomainService.domainDtoMaker(domain);
            createdDeal.setDomain(createdDomain);

            CreatedBid createdBid = BidService.bidDtoMaker(bid);
            createdDeal.setBid(createdBid);

            CustomerDto customerDto = CustomerService.fromCustomer(customer);
            createdDeal.setDto(customerDto);
        }


        return createdDeal;
    }
}
