package krystianrytel.sklepaukcyjny.config;

import krystianrytel.sklepaukcyjny.models.*;
import krystianrytel.sklepaukcyjny.repositories.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@Configuration
@Log4j2
public class RepositoriesInitializer {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private void InsertSomeCategories(){
        if( categoryRepository.findAll().isEmpty() ){
            Category cat1 = new Category("Motoryzacja");
            categoryRepository.save(cat1);
            categoryRepository.save(new Category("Nieruchomość"));
            categoryRepository.save(new Category("Elektronika"));
            categoryRepository.save(new Category("Moda"));
            categoryRepository.save(new Category("Dom i ogród"));
            categoryRepository.save(new Category("Muzyka i Edukacja"));
            categoryRepository.save(new Category("Sprot i Hobby"));
            categoryRepository.save(new Category("Rolnictwo"));
            log.info("RepositoryInitializer: " + "Added some item categories!");
        }else{
            log.info("RepositoryInitializer: " + "Item categories is exists in database.");
        }
    }

    private void InsertSomeItems(){
        if( itemRepository.findAll().isEmpty() ){
            Category cat1 = categoryRepository.getOne((long) 4 );
            Item item = new Item("",50.99f, cat1, new Date(), 5, "Opis...");
            item.setName("Bluzka Damska");
            item.setCategory(cat1);
            item.setPhotopath("statics/images/items/1/1.jpg");
            itemRepository.save(item);
            item = new Item("Bluzka męska",30f, cat1, new Date(), 10, "Opis...");
            item.setPhotopath("statics/images/items/2/2.jpg");
            itemRepository.save( item );
            item = new Item("Spodnie męskie", 40f, cat1, new Date(), 20, "Opis...");
            item.setPhotopath("statics/images/items/3/3.jpg");
            itemRepository.save( item );
            item = new Item("Koszula w kratę", (float)50.50, cat1, new Date(), 40, "Opis...");
            item.setPhotopath("statics/images/items/4/4.jpg");
            itemRepository.save( item );
            log.info("RepositoryInitializer: " + "Added some items!");
        }else{
            log.info("RepositoryInitializer: " + "Items are exists in database.");
        }
    }

    private void InsertSomeAuctions(){
        if( auctionRepository.findAll().isEmpty() ){
            Item item = new Item();
            item.setCategory(categoryRepository.getOne((long)7));
            item.setQuantity(1);
            item.setName("Obraz");
            item.setIsauction(true);
            item.setPrice(1000f);
            item.setDescription("Obraz znanego artysty");
            item.setPhotopath("statics/images/items/5/5.jpeg");
            itemRepository.save(item);

            Date expirationDate = new Date();
            expirationDate.setYear(2018);
            expirationDate.setMonth(1);
            expirationDate.setDate(31);
            expirationDate.setHours(24);

            Auction auction = new Auction(item, 100f, new Date(), expirationDate);
            auctionRepository.save(auction);
            log.info("RepositoryInitializer: " + "Added some auctions!");
        }else{
            log.info("RepositoryInitializer: " + "Auctions are exists in database.");
        }
    }

    private void InsertBasicUsers(){
        if(roleRepository.findAll().isEmpty()){
            try {
                Role roleUser = roleRepository.save(new Role(Role.Types.ROLE_USER));
                Role roleAdmin = roleRepository.save(new Role(Role.Types.ROLE_ADMIN));

                User user = new User("user", true);
                user.setRoles(new HashSet<>(Arrays.asList(roleUser)));
                user.setPassword(passwordEncoder.encode("user"));
                user.setWallet( 1000.00f );
                user.setFirstname("Użytkownik");
                user.setSurname("Testowy");
                user.setAdress("Testowa 12 / 3");
                user.setCity("Testowo");
                user.setPostcode("00-000");

                User admin = new User("admin", true);
                admin.setRoles(new HashSet<>(Arrays.asList(roleAdmin)));
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setWallet( 1000.00f );


                User test = new User("krystian", true);
                test.setRoles(new HashSet<>(Arrays.asList(roleAdmin, roleUser)));
                test.setPassword(passwordEncoder.encode("krystian"));
                test.setWallet( 1000.00f );
                test.setFirstname("Krystian");
                test.setSurname("Rytel");
                test.setAdress("Unitów Podlaskich 11 / 2");
                test.setCity("Siedlce");
                test.setPostcode("00-000");

                userRepository.save(user);
                userRepository.save(admin);
                userRepository.save(test);
                log.info("RepositoryInitializer: " + "Added basic users!");
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            log.info("RepositoryInitializer: " + "Users are exists in database.");
        }
    }
    @Bean
    InitializingBean init() {
        return () -> {
            InsertBasicUsers();
            InsertSomeCategories();
            InsertSomeItems();
            InsertSomeAuctions();
        };
    }
}
