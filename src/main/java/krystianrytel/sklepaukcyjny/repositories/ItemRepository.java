package krystianrytel.sklepaukcyjny.repositories;

import krystianrytel.sklepaukcyjny.models.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.PositiveOrZero;

public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

    Page<Item> findAllByIsauctionFalse(Pageable pageable);


    @Query(value = "SELECT v " +
            "FROM Item v " +
            "WHERE v.isauction = false AND (:phrase is null OR :phrase = '' OR " +
            "upper(v.name) LIKE upper(:phrase) OR " +
            "upper(v.category.name) LIKE upper(:phrase)) " +
            "AND (:min is null OR :min <= v.price) " +
            "AND (:max is null OR :max >= v.price)")
    Page<Item> findAllItemsByIsauctionFalseUsingFilter(@Param("phrase") String p, @Param("min") Float priceMin, @Param("max") Float priceMax, Pageable pageable);


}

