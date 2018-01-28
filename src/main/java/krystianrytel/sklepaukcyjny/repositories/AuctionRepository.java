package krystianrytel.sklepaukcyjny.repositories;

import krystianrytel.sklepaukcyjny.models.Auction;
import krystianrytel.sklepaukcyjny.models.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuctionRepository extends JpaRepository<Auction, Long>, JpaSpecificationExecutor<Auction> {


    @Query(value = "SELECT v " +
            "FROM Auction v " +
            "WHERE (:phrase is null OR :phrase = '' OR " +
            "upper(v.name) LIKE upper(:phrase) OR " +
            "upper(v.item.category.name) LIKE upper(:phrase)) " +
            "AND (:min is null OR :min <= v.item.price) " +
            "AND (:max is null OR :max >= v.item.price)")
    Page<Auction> findAllAuctionsUsingFilter(@Param("phrase") String p, @Param("min") Float priceMin, @Param("max") Float priceMax, Pageable pageable);

}