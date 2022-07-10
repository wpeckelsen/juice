package nl.wessel.juice.a.Controller.Publisher;

import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreateDomain;
import nl.wessel.juice.B.BusinessLogic.DTO.Domain.CreatedDomain;
import nl.wessel.juice.B.BusinessLogic.Service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "juice/domain")
public class DomainContr {


    @Autowired
    DomainService domainService;

    //    CREATE
//    @PostMapping("/new")
//    public ResponseEntity<CreatedDomain> newDomain(@RequestBody CreateDomain domain) {
//        final CreatedDomain createdDomain = domainService.newDomain(domain);
//        return ResponseEntity.ok().body(createdDomain);
//    }


    //    READ
    @GetMapping("/list")
    public ResponseEntity<List<CreatedDomain>> getList() {
        List<CreatedDomain> createdDomainList;
        createdDomainList = domainService.getList();
        return ResponseEntity.ok().body(createdDomainList);
    }

    @GetMapping("/{domainID}")
    public ResponseEntity<CreatedDomain> getByID(@PathVariable("domainID") Long domainID) {
        CreatedDomain createdDomain = domainService.getByID(domainID);
        return ResponseEntity.ok().body(createdDomain);
    }


    //    update
    @PutMapping("/update/{domainID}")
    public ResponseEntity<Object> update(@PathVariable Long domainID, @RequestBody CreateDomain createDomain) {
        CreatedDomain createdDomain = domainService.update(domainID, createDomain);
        return ResponseEntity.ok().body(createdDomain);
    }


    //    delete
    @DeleteMapping("/delete/{domainID}")
    public ResponseEntity<Object> deleteById(@PathVariable Long domainID) {
        domainService.deleteById(domainID);
        return ResponseEntity.noContent().build();
    }
}
