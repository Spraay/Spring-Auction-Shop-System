package krystianrytel.sklepaukcyjny.repositories;

import krystianrytel.sklepaukcyjny.models.Cart;
import krystianrytel.sklepaukcyjny.models.Item;
import krystianrytel.sklepaukcyjny.models.TheOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<TheOrder, Long>, JpaSpecificationExecutor<TheOrder> {

    @Query("SELECT v FROM TheOrder v WHERE " +
            "upper(v.item_name) LIKE upper(:phrase)")
    Page<TheOrder> findByNameContaining(String phrase, Pageable pageable);
    

    //nad klasą Vehicle znajduje się definicja zapytania (@NamedQuery) powiązana z tą metodą
    Page<TheOrder> findAllOrdersUsingNamedQuery(String phrase, Pageable pageable);

    @Query("SELECT v FROM TheOrder v WHERE " +
            "(" +
            ":phrase is null OR :phrase = '' OR "+
            "upper(v.item_name) LIKE upper(:phrase) OR " +
            "upper(v.category.name) LIKE upper(:phrase)" +
            ") ")
    Page<TheOrder> findAllOrdersUsingFilter(@Param("phrase") String p, Pageable pageable);


    @Query("SELECT v "+
            "FROM TheOrder v WHERE " +
            "(" + "upper(v.user_name) LIKE upper(:username)" + ") "
    )
    List<TheOrder> getUserOrders(@Param("username") String username);
}