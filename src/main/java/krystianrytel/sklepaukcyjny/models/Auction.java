package krystianrytel.sklepaukcyjny.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "auctions")
@Getter
@Setter
@NamedQuery(name = "Auction.findAllAuctionsUsingNamedQuery",
        query = "SELECT v FROM Auction v WHERE upper(v.name) LIKE upper(:phrase)")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)//EAGER powoduje zaciągnięcie obiektu VehicleType wraz z obiektem Vehicle.
    @JoinColumn(name="user_id", nullable = true)
    private User user = null;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="item_id", nullable = false)
    private Item item;

    private String name;

    @Column(name="created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name="expiration_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    private float minbidrise;

    public Auction() {
        this.createdDate = new Date();
        this.item = new Item();
    }

    public Auction(Item item, float minbidrise, Date createdDate, Date expirationDate) {
        this.item = item;
        this.name = item.getName();
        this.minbidrise = minbidrise;
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
    }
}
