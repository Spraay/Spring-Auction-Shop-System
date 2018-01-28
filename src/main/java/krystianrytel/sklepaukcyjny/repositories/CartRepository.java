package krystianrytel.sklepaukcyjny.repositories;

import krystianrytel.sklepaukcyjny.models.Cart;
import krystianrytel.sklepaukcyjny.models.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long>, JpaSpecificationExecutor<Cart> {
    @Query("SELECT v "+
            "FROM Cart v WHERE " +
            "(" + "upper(v.username) LIKE upper(:username)" + ") "
    )
    List<Cart> getUserCart(@Param("username") String param);

}