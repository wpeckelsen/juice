package nl.wessel.juice.B.BusinessLogic.Service;



import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreateDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreatedDomain;

import nl.wessel.juice.B.BusinessLogic.Exception.RecordNotFound;
import nl.wessel.juice.B.BusinessLogic.Model.Domain;
import nl.wessel.juice.C.Repository.DomainRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DomainService {

    private final DomainRepo domainRepo;

    public DomainService(DomainRepo domainRepo) {
        this.domainRepo = domainRepo;
    }

    public Domain domainMaker(CreateDomain createDomain){
        Domain domain = new Domain();
        domain.setName(createDomain.getName());
        domain.setTLD(createDomain.getTLD());
        domain.setCategory(createDomain.getCategory());
        domain.setPrice(createDomain.getPrice());
        return domain;
    }

    public static CreatedDomain domainDtoMaker(Domain domain){
        CreatedDomain createdDomain = new CreatedDomain();
        createdDomain.setDomainID(domain.getDomainID());
        createdDomain.setName(domain.getName());
        createdDomain.setTLD(domain.getTLD());
        createdDomain.setCategory(domain.getCategory());
        createdDomain.setPrice(domain.getPrice());
        return createdDomain;
    }

// create
    public CreatedDomain newDomain(CreateDomain createDomain){
        Domain domain = domainMaker(createDomain);
        domainRepo.save(domain);
        return domainDtoMaker(domain);
    }

//    read
public List<CreatedDomain> getList() {
    List<Domain> domainList = domainRepo.findAll();
    List<CreatedDomain> createdDomainList = new ArrayList<>();

    for (Domain domain : domainList) {
        CreatedDomain createdDomain = domainDtoMaker(domain);
        createdDomainList.add(createdDomain);
    }
    return createdDomainList;
}


    public List<CreatedDomain> getListByName(String name) {
        List<Domain> domainList = domainRepo.findDomainsByName(name);
        List<CreatedDomain> createdDomainList = new ArrayList<>();

        for (Domain domain : domainList) {
            CreatedDomain createdDomain = domainDtoMaker(domain);
            createdDomainList.add(createdDomain);
        }
        return createdDomainList;
    }

    public CreatedDomain getByID(Long idDomain) {
        if (domainRepo.findById(idDomain).isPresent()) {
            Domain domain = domainRepo.findById(idDomain).get();
            return domainDtoMaker(domain);
        } else {
            Domain domain = new Domain();
            throw new RecordNotFound(domain);
        }
    }



    //    update
    public CreatedDomain update(Long domainID, CreateDomain createDomain) {
        if (domainRepo.findById(domainID).isPresent()) {
            Domain domain = domainRepo.findById(domainID).get();
            Domain domain1 = domainMaker(createDomain);

            domain1.setDomainID(domain.getDomainID());
            domainRepo.save(domain1);
            return domainDtoMaker(domain1);
        } else {
            Domain domain = new Domain();
            throw new RecordNotFound(domain);
        }
    }

    //    delete

    public void deleteById(Long domainID) {
        domainRepo.deleteById(domainID);
    }

}
