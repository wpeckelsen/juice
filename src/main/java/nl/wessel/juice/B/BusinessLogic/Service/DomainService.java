package nl.wessel.juice.B.BusinessLogic.Service;

import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreateDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreatedDomain;
import nl.wessel.juice.B.BusinessLogic.Exception.RecordNotFound;
import nl.wessel.juice.B.BusinessLogic.Model.Domain;
import nl.wessel.juice.C.Repository.DomainRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class DomainService {

    private final DomainRepo domainRepo;

    @Autowired
    public DomainService(DomainRepo domainRepo) {
        this.domainRepo = domainRepo;
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


    //    read
    public List<CreatedDomain> getList() {
        List<Domain> domainList = domainRepo.findAll();
        if (domainList.isEmpty()) {
            Domain domain = new Domain();
            throw new RecordNotFound(domain);
        } else {
            List<CreatedDomain> createdDomainList = new ArrayList<>();

            for (Domain domain : domainList) {
                CreatedDomain createdDomain = domainDtoMaker(domain);
                createdDomainList.add(createdDomain);
            }
            return createdDomainList;
        }
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
