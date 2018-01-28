package krystianrytel.sklepaukcyjny.services;

import krystianrytel.sklepaukcyjny.controllers.commands.AuctionFilter;
import krystianrytel.sklepaukcyjny.controllers.commands.ItemFilter;
import krystianrytel.sklepaukcyjny.models.Auction;
import krystianrytel.sklepaukcyjny.models.Category;
import krystianrytel.sklepaukcyjny.models.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuctionService {

    Page<Auction> getAllAuctions(AuctionFilter filter, Pageable pageable);

    Auction getAuction(Long id);

    boolean isExists(Auction auction);

    void deleteAuction(Long id);

    void saveAuction(Auction auction);
}
