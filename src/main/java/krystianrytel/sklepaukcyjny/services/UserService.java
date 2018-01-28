package krystianrytel.sklepaukcyjny.services;

import krystianrytel.sklepaukcyjny.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.math.BigDecimal;

public interface UserService extends UserDetailsService {
// Własne metody
    void save(User user);

    boolean isUniqueLogin(String login);

    void delete(Long id);

    User getUser(String name);
}
