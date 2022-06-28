package nl.wessel.juice.C.Repository;

import nl.wessel.juice.B.BusinessLogic.Model.Market;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketRepo extends JpaRepository<Market, Long> {
    List<Market> findMarketsByName(String name);
}
