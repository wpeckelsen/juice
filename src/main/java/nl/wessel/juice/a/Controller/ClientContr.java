package nl.wessel.juice.a.Controller;


import nl.wessel.juice.B.BusinessLogic.DTO.Client.CreateClient;
import nl.wessel.juice.B.BusinessLogic.DTO.Client.CreatedClient;
import nl.wessel.juice.B.BusinessLogic.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/client")
public class ClientContr {

    ClientService clientService;

    @Autowired
    public ClientContr(ClientService clientService) {
        this.clientService = clientService;
    }




    //    CREATE
    @PostMapping("/new")
    public ResponseEntity<CreatedClient> newClient(@RequestBody CreateClient client) {
        final CreatedClient createdClient = clientService.newClient(client);
        return ResponseEntity.ok().body(createdClient);


    }


    //    READ
    @GetMapping("/list")
    public ResponseEntity<List<CreatedClient>> getList() {
        List<CreatedClient> createdClientList;
        createdClientList = clientService.getList();
        return ResponseEntity.ok().body(createdClientList);
    }

    @GetMapping("/{clientID}")
    public ResponseEntity<CreatedClient> getByID(@PathVariable("clientID") Long clientID) {
        CreatedClient createdClient = clientService.getByID(clientID);
        return ResponseEntity.ok().body(createdClient);
    }


    //    update
    @PutMapping("/update/{clientID}")
    public ResponseEntity<Object> update(@PathVariable Long clientID, @RequestBody CreateClient createClient) {
        CreatedClient createdClient = clientService.update(clientID, createClient);
        return ResponseEntity.ok().body(createdClient);
    }


    //    delete
    @DeleteMapping("/delete/{clientID}")
    public ResponseEntity<Object> deleteById(@PathVariable Long clientID) {
        clientService.deleteById(clientID);
        return ResponseEntity.noContent().build();
    }


    //    assign
    @PutMapping("/assign/client{clientID}/bid/{bidID}")
    public ResponseEntity<CreatedClient> assignBids(@PathVariable Long clientID,
                                                      @PathVariable Long bidID) {
        return ResponseEntity.ok().body(clientService.assignBids(clientID, bidID));
    }
}
