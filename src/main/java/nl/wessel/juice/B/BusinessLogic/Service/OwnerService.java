package nl.wessel.juice.B.BusinessLogic.Service;


import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreateBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Bid.CreatedBid;
import nl.wessel.juice.B.BusinessLogic.DTO.Customer.CustomerDto;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreateDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreatedDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Owner.OwnerDto;
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

import static nl.wessel.juice.B.BusinessLogic.Service.BidService.bidMaker;
import static nl.wessel.juice.B.BusinessLogic.Service.DomainService.domainMaker;

@Service
@Transactional
public class OwnerService {

    private final CustomerRepo customerRepo;
    private final BidRepo bidRepo;
    private final DomainRepo domainRepo;
    private final DealRepo dealRepo;
    private final PublisherRepo publisherRepo;
    private final OwnerRepo ownerRepo;
    private final DomainService domainService;

    @Autowired
    public OwnerService(CustomerRepo customerRepo, BidRepo bidRepo, DomainRepo domainRepo, DealRepo dealRepo, PublisherRepo publisherRepo, OwnerRepo ownerRepo, DomainService domainService) {
        this.customerRepo = customerRepo;
        this.bidRepo = bidRepo;
        this.domainRepo = domainRepo;
        this.dealRepo = dealRepo;
        this.publisherRepo = publisherRepo;
        this.ownerRepo = ownerRepo;
        this.domainService = domainService;
    }

    public List<OwnerDto> getOwners() {
        List<OwnerDto> collection = new ArrayList<>();
        List<Owner> list = ownerRepo.findAll();
        for (Owner owner : list) {
            collection.add(fromOwner(owner));
        }
        return collection;
    }

    public OwnerDto getOwner(String ownerName) {
        OwnerDto dto = new OwnerDto();
        var owner = ownerRepo.findById(ownerName);
        if (owner.isPresent()) {
            dto = fromOwner(owner.get());
        } else {
            throw new UsernameNotFound(ownerName);
        }
        return dto;
    }

    public boolean ownerExists(String ownerName) {
        return ownerRepo.existsById(ownerName);
    }

    public String createOwner(OwnerDto ownerDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        ownerDto.setApikey(randomString);
        Owner newOwner = ownerRepo.save(toOwner(ownerDto));
        return newOwner.getUsername();
    }

    public void deleteOwner(String ownerName) {
        ownerRepo.deleteById(ownerName);
    }

    public void updateOwner(String ownerName, OwnerDto newOwner) {
        if (!ownerRepo.existsById(ownerName)) throw new BadRequest();
        Owner owner = ownerRepo.findById(ownerName).get();
        owner.setPassword(newOwner.getPassword());
        ownerRepo.save(owner);
    }

    public Set<Authority> getAuthorities(String ownerName) {
        if (!ownerRepo.existsById(ownerName)) throw new UsernameNotFound(ownerName);
        Owner owner = ownerRepo.findById(ownerName).get();
        OwnerDto OwnerDto = fromOwner(owner);
        return OwnerDto.getAuthorities();
    }

    public void addAuthority(String ownerName, String authority) {

        if (!ownerRepo.existsById(ownerName)) throw new UsernameNotFound(ownerName);
        Owner owner = ownerRepo.findById(ownerName).get();
        owner.addAuthority(new Authority(ownerName, authority));
        ownerRepo.save(owner);
    }

    public void removeAuthority(String ownerName, String authority) {
        if (!ownerRepo.existsById(ownerName)) throw new UsernameNotFound(ownerName);
        Owner owner = ownerRepo.findById(ownerName).get();
        Authority authorityToRemove = owner.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        owner.removeAuthority(authorityToRemove);
        ownerRepo.save(owner);
    }

    public static OwnerDto fromOwner(Owner owner) {

        var dto = new OwnerDto();
        dto.username = owner.getUsername();
        dto.password = owner.getPassword();
        dto.enabled = owner.isEnabled();
        dto.apikey = owner.getApikey();
        dto.email = owner.getEmail();
        dto.authorities = owner.getAuthorities();


        List<Domain> domains = owner.getDomains();
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

    public Owner toOwner(OwnerDto ownerDto) {


        var owner = new Owner();
        owner.setUsername(ownerDto.getUsername());
        owner.setPassword(ownerDto.getPassword());
        owner.setEnabled(ownerDto.getEnabled());
        owner.setApikey(ownerDto.getApikey());
        owner.setEmail(ownerDto.getEmail());

        return owner;
    }



    public OwnerDto newDomain(CreateDomain createDomain, String username) {

        var optCustom = ownerRepo.findById(username);


        if (optCustom.isPresent()) {
            Owner owner = optCustom.get();
            Domain newDomain = domainMaker(createDomain);

            List<Domain> currentDomains = owner.getDomains();
            currentDomains.add(newDomain);

            for (Domain domain : currentDomains) {
                domain.setOwner(owner);
                domainRepo.save(domain);
            }

            owner.setDomains(currentDomains);
            ownerRepo.save(owner);
            return fromOwner(owner);

        } else {

            throw new BadRequest("A domain cannot be made without a Owner. Make an Owner first");
        }
    }


}
