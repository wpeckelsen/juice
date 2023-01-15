package nl.wessel.juice.Service;

import nl.wessel.juice.DTO.Domain.CreateDomainDto;
import nl.wessel.juice.DTO.Domain.CreatedDomainDto;
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

    public static CreatedDomainDto domainDtoMaker(Domain domain) {
        CreatedDomainDto createdDomainDTO = new CreatedDomainDto();
        createdDomainDTO.setDomainID(domain.getDomainID());
        createdDomainDTO.setName(domain.getName());
        createdDomainDTO.setTLD(domain.getTLD());
        createdDomainDTO.setCategory(domain.getCategory());
        createdDomainDTO.setPrice(domain.getPrice());
        return createdDomainDTO;
    }

    public static Domain domainMaker(CreateDomainDto createDomainDto) {
        Domain domain = new Domain();
        domain.setName(createDomainDto.getName());
        domain.setTLD(createDomainDto.getTLD());
        domain.setCategory(createDomainDto.getCategory());
        domain.setPrice(createDomainDto.getPrice());
        return domain;
    }


    public List<CreatedDomainDto> getList() {
        List<Domain> domainList = domainRepository.findAll();
        if (domainList.isEmpty()) {
            Domain domain = new Domain();
            throw new RecordNotFound(domain);
        } else {
            List<CreatedDomainDto> createdDomainDtoList = new ArrayList<>();

            for (Domain domain : domainList) {
                CreatedDomainDto createdDomainDTO = domainDtoMaker(domain);
                createdDomainDtoList.add(createdDomainDTO);
            }
            return createdDomainDtoList;
        }
    }


    public CreatedDomainDto getByID(Long idDomain) {
        if (domainRepository.findById(idDomain).isPresent()) {
            Domain domain = domainRepository.findById(idDomain).get();
            return domainDtoMaker(domain);
        } else {
            Domain domain = new Domain();
            throw new RecordNotFound(domain);
        }
    }


    public CreatedDomainDto update(Long domainID, CreateDomainDto createDomainDto) {
        if (domainRepository.findById(domainID).isPresent()) {
            Domain domain = domainRepository.findById(domainID).get();
            Domain domain1 = domainMaker(createDomainDto);

            domain1.setDomainID(domain.getDomainID());
            domainRepository.save(domain1);
            return domainDtoMaker(domain1);
        } else {
            Domain domain = new Domain();
            throw new RecordNotFound(domain);
        }
    }


    public void deleteById(Long domainID) {
        domainRepository.deleteById(domainID);
    }

}
