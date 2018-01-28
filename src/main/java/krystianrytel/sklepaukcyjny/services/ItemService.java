package krystianrytel.sklepaukcyjny.services;

import krystianrytel.sklepaukcyjny.controllers.commands.ItemFilter;
import krystianrytel.sklepaukcyjny.models.Cart;
import krystianrytel.sklepaukcyjny.models.Category;
import krystianrytel.sklepaukcyjny.models.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    List<Category> getAllTypes();

    Page<Item> getAllItems(ItemFilter filter, Pageable pageable);

    Item getItem(Long id);

    boolean isExists(Item item);

    void deleteItem(Long id);

    void saveItem(Item item);
}
