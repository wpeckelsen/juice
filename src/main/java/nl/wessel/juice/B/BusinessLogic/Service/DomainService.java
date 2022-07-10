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

    // create
//    public CreatedDomain newDomain(CreateDomain createDomain){
//        Domain domain = TransferService.domainMaker(createDomain);
//        domainRepo.save(domain);
//        return TransferService.domainDtoMaker(domain);
//    }

//    read
public List<CreatedDomain> getList() {
    List<Domain> domainList = domainRepo.findAll();
        if(domainList.isEmpty()){
            Domain domain = new Domain();
            throw new RecordNotFound(domain);
        } else {
            List<CreatedDomain> createdDomainList = new ArrayList<>();

            for (Domain domain : domainList) {
                CreatedDomain createdDomain = TransferService.domainDtoMaker(domain);
                createdDomainList.add(createdDomain);
            }
            return createdDomainList;
        }
}


//    public List<CreatedDomain> getListByName(String name) {
//        List<Domain> domainList = domainRepo.findDomainsByName(name);
//        List<CreatedDomain> createdDomainList = new ArrayList<>();
//
//        for (Domain domain : domainList) {
//            CreatedDomain createdDomain = TransferService.domainDtoMaker(domain);
//            createdDomainList.add(createdDomain);
//        }
//        return createdDomainList;
//    }

    public CreatedDomain getByID(Long idDomain) {
        if (domainRepo.findById(idDomain).isPresent()) {
            Domain domain = domainRepo.findById(idDomain).get();
            return TransferService.domainDtoMaker(domain);
        } else {
            Domain domain = new Domain();
            throw new RecordNotFound(domain);
        }
    }



    //    update
    public CreatedDomain update(Long domainID, CreateDomain createDomain) {
        if (domainRepo.findById(domainID).isPresent()) {
            Domain domain = domainRepo.findById(domainID).get();
            Domain domain1 = TransferService.domainMaker(createDomain);

            domain1.setDomainID(domain.getDomainID());
            domainRepo.save(domain1);
            return TransferService.domainDtoMaker(domain1);
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
