package nl.wessel.juice.B.BusinessLogic.Service;


import nl.wessel.juice.B.BusinessLogic.DTO.Client.CreateClient;
import nl.wessel.juice.B.BusinessLogic.DTO.Client.CreatedClient;
import nl.wessel.juice.B.BusinessLogic.DTO.Order.CreatedOrder;
import nl.wessel.juice.B.BusinessLogic.Exception.RecordNotFound;
import nl.wessel.juice.B.BusinessLogic.Model.Client;
import nl.wessel.juice.B.BusinessLogic.Model.Order;
import nl.wessel.juice.C.Repository.ClientRepo;
import nl.wessel.juice.C.Repository.DealRepo;
import nl.wessel.juice.C.Repository.OrderRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepo clientRepo;
    private final DealRepo dealRepo;
    private final OrderRepo orderRepo;

    private final OrderService orderService;

    public ClientService(ClientRepo clientRepo, DealRepo dealRepo, OrderRepo orderRepo, OrderService orderService) {
        this.clientRepo = clientRepo;
        this.dealRepo = dealRepo;
        this.orderRepo = orderRepo;
        this.orderService = orderService;
    }

    public static CreatedClient clientDtoMaker(Client client) {
        CreatedClient createdClient = new CreatedClient();
        createdClient.setClientID(client.getClientID());
        createdClient.setEmail(client.getEmail());
        createdClient.setName(client.getName());
        createdClient.setPassword(client.getPassword());
//        createdClient.setMarkets(client.getMarkets());
//        createdClient.setDeals(client.getDeals());

        List<Order> orders = client.getOrders();
        List<CreatedOrder> createdOrders = new ArrayList<>();

        if(orders != null){
            for(Order order : orders){
                CreatedOrder createdOrder = OrderService.orderDtoMaker(order);
                createdOrders.add(createdOrder);
            }
        }

        client.getOrders();
        createdClient.setOrders(createdOrders);
        return createdClient;

    }


    public static Client clientMaker(CreateClient createClient) {
        Client client = new Client();
        client.setEmail(createClient.getEmail());
        client.setName(createClient.getName());
        client.setPassword(createClient.getPassword());
        return client;
    }


    //    CREATE
    public CreatedClient newClient(CreateClient createClient) {
        Client client = clientMaker(createClient);
        clientRepo.save(client);
        return clientDtoMaker(client);
    }

    //    READ
    public List<CreatedClient> getList() {
        List<Client> clientList = clientRepo.findAll();
        List<CreatedClient> createdClientList = new ArrayList<>();

        for (Client client : clientList) {
            CreatedClient createdClient = clientDtoMaker(client);
            createdClientList.add(createdClient);
        }
        return createdClientList;
    }

    public List<CreatedClient> getListByName(String name) {
        List<Client> clientList = clientRepo.findClientsByName(name);
        List<CreatedClient> createdClientList = new ArrayList<>();

        for (Client client : clientList) {
            CreatedClient createdClient = clientDtoMaker(client);
            createdClientList.add(createdClient);
        }
        return createdClientList;
    }

    public CreatedClient getByID(Long idClient) {
        if (clientRepo.findById(idClient).isPresent()) {
            Client client = clientRepo.findById(idClient).get();
            return clientDtoMaker(client);
        } else {
            Client client = new Client();
            throw new RecordNotFound(client);
        }
    }

    //    update
    public CreatedClient update(Long clientID, CreateClient createClient) {


        if (clientRepo.findById(clientID).isPresent()) {
            Client client = clientRepo.findById(clientID).get();
            Client client1 = clientMaker(createClient);

            client1.setClientID(client.getClientID());
            clientRepo.save(client1);
            return clientDtoMaker(client1);
        } else {
            Client client = new Client();
            throw new RecordNotFound(client);
        }
    }

    //    delete
    public void deleteById(Long clientID) {
        clientRepo.deleteById(clientID);
    }


//    assign to methods
    public CreatedClient assignOrders(Long idClient, Long idOrder) {

        Optional<Client> optionalClient = clientRepo.findById(idClient);
        Client client = optionalClient.get();

        Optional<Order> optionalOrder = orderRepo.findById(idOrder);
        Order newOrder = optionalOrder.get();

        List<Order> currentOrders = client.getOrders();
        currentOrders.add(newOrder);

        for (Order order : currentOrders) {
            order.setClient(client);
            orderRepo.save(order);
        }

        client.setOrders(currentOrders);
        clientRepo.save(client);
        return clientDtoMaker(client);
    }






}


