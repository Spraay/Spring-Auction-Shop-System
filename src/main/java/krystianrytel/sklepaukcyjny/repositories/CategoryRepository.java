package krystianrytel.sklepaukcyjny.repositories;

import krystianrytel.sklepaukcyjny.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
