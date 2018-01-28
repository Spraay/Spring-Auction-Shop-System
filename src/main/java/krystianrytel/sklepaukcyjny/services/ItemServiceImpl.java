package krystianrytel.sklepaukcyjny.services;

import krystianrytel.sklepaukcyjny.controllers.commands.ItemFilter;
import krystianrytel.sklepaukcyjny.exceptions.ItemNotFoundException;
import krystianrytel.sklepaukcyjny.models.Cart;
import krystianrytel.sklepaukcyjny.models.Category;
import krystianrytel.sklepaukcyjny.models.Item;
import krystianrytel.sklepaukcyjny.models.User;
import krystianrytel.sklepaukcyjny.repositories.CartRepository;
import krystianrytel.sklepaukcyjny.repositories.CategoryRepository;
import krystianrytel.sklepaukcyjny.repositories.ItemRepository;
import krystianrytel.sklepaukcyjny.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<Category> getAllTypes() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Item> getAllItems(ItemFilter search, Pageable pageable) {
        Page page;
        if(search.isEmpty()){
            page = itemRepository.findAllByIsauctionFalse(pageable);
        }else{
            page = itemRepository.findAllItemsByIsauctionFalseUsingFilter(search.getPhraseLIKE(), search.getMinPrice(), search.getMaxPrice(),  pageable);
        }

        return page;

    }

    @Transactional
    @Override
    public Item getItem(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        Item item = optionalItem.orElseThrow(()->new ItemNotFoundException(id));
        return item;
    }

    @Override
    public boolean isExists(Item item) {
        return itemRepository.existsById(item.getId());
    }

    @Override
    public void deleteItem(Long item_id) {
        if(itemRepository.existsById(item_id) == true){
            itemRepository.deleteById(item_id);
        }else{
            throw new ItemNotFoundException(item_id);
        }
    }

    @Override
    public void saveItem(Item item) {
        itemRepository.save(item);
    }
}
