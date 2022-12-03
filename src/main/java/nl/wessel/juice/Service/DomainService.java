package nl.wessel.juice.Service;

import nl.wessel.juice.DTO.Domain.CreateDomain;
import nl.wessel.juice.DTO.Domain.CreatedDomain;
import nl.wessel.juice.Exception.RecordNotFound;
import nl.wessel.juice.Model.Domain;
import nl.wessel.juice.Repository.DomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class DomainService {

    private final DomainRepository domainRepository;

    @Autowired
    public DomainService(DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
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
        List<Domain> domainList = domainRepository.findAll();
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
        if (domainRepository.findById(idDomain).isPresent()) {
            Domain domain = domainRepository.findById(idDomain).get();
            return domainDtoMaker(domain);
        } else {
            Domain domain = new Domain();
            throw new RecordNotFound(domain);
        }
    }




    //    update
    public CreatedDomain update(Long domainID, CreateDomain createDomain) {
        if (domainRepository.findById(domainID).isPresent()) {
            Domain domain = domainRepository.findById(domainID).get();
            Domain domain1 = domainMaker(createDomain);

            domain1.setDomainID(domain.getDomainID());
            domainRepository.save(domain1);
            return domainDtoMaker(domain1);
        } else {
            Domain domain = new Domain();
            throw new RecordNotFound(domain);
        }
    }

    //    delete
    public void deleteById(Long domainID) {
        domainRepository.deleteById(domainID);
    }

}
