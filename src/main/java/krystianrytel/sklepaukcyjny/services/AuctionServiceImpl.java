package krystianrytel.sklepaukcyjny.services;

import krystianrytel.sklepaukcyjny.controllers.commands.AuctionFilter;
import krystianrytel.sklepaukcyjny.controllers.commands.ItemFilter;
import krystianrytel.sklepaukcyjny.exceptions.AuctionNotFoundException;
import krystianrytel.sklepaukcyjny.exceptions.ItemNotFoundException;
import krystianrytel.sklepaukcyjny.models.Auction;
import krystianrytel.sklepaukcyjny.models.Category;
import krystianrytel.sklepaukcyjny.models.Item;
import krystianrytel.sklepaukcyjny.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuctionServiceImpl implements AuctionService {


    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Page<Auction> getAllAuctions(AuctionFilter search, Pageable pageable) {
        Page page;
        if(search.isEmpty()){
            page = auctionRepository.findAll(pageable);
        }else{
            page = auctionRepository.findAllAuctionsUsingFilter(search.getPhraseLIKE(), search.getMinPrice(), search.getMaxPrice(),  pageable);
        }

        return page;

    }

    @Transactional
    @Override
    public Auction getAuction(Long id) {
        Optional<Auction> optionalAuction = auctionRepository.findById(id);
        Auction auction = optionalAuction.orElseThrow(()->new AuctionNotFoundException(id));
        return auction;
    }

    @Override
    public boolean isExists(Auction auction) {
        return auctionRepository.existsById(auction.getId());
    }

    @Override
    public void deleteAuction(Long id) {
        if(auctionRepository.existsById(id)){
            itemRepository.deleteById(auctionRepository.getOne(id).getItem().getId());
            auctionRepository.deleteById(id);
        }else{
            throw new AuctionNotFoundException(id);
        }
    }

    @Override
    public void saveAuction(Auction auction) {
        auctionRepository.save(auction);
    }
}
