package nl.wessel.juice.Service;

import nl.wessel.juice.Model.Bid;
import nl.wessel.juice.Repository.BidRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BidServiceTest {

    @Mock
    BidRepository bidRepository;

    @InjectMocks
    BidService bidService;

    @Captor
    ArgumentCaptor<Object> captor;

    @Test
    @Disabled
    void bidMaker() {
    }

    @Test
    @Disabled
    void bidDtoMaker() {
    }

    @Test
    @Disabled
    void getList() {
//        Bid bid1 = new Bid();
//        Long id1 = Long.valueOf(99);
//        bid1.setBidID(id1);
//
//        Bid bid2 = new Bid();
//        Long id2 = Long.valueOf(100);
//        bid2.setBidID(id2);
//
//        List<Long> bids= bidService.getList();
//
//
//        when(bidRepository.findAll()).thenReturn(bids);
//        assertEquals(bids,);

    }



    @Test
    @Disabled
    void getByID() {
        Bid bid = new Bid();
        Long id = Long.valueOf(99);
        String topic = "horse";

        bid.setBidID(id);
        bid.setTopic(topic);

        when(bidRepository.findById(id)).thenReturn(Optional.of(bid));
        assertEquals(99, bid.getBidID());
//        error
    }

    @Test
    @Disabled
    void update() {
    }

    @Test
    @Disabled
    void delete() {
    }
}