package krystianrytel.sklepaukcyjny.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;

    @ManyToOne(fetch = FetchType.EAGER)//EAGER powoduje zaciągnięcie obiektu VehicleType wraz z obiektem Vehicle.
    @JoinColumn(name="item_id", nullable = false)
    private Item item;
}
