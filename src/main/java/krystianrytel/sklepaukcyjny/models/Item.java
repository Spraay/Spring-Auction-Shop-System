package krystianrytel.sklepaukcyjny.models;

import krystianrytel.sklepaukcyjny.validators.annotations.InvalidValues;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "items")
@Getter @Setter
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Length(min = 2, max = 30)// lub @Size(min = 2, max = 30)
    @InvalidValues(ignoreCase = true, values = {"XXX", "YYY"})
    private String name;

    @Positive
    @Max(1000000)
    private float price;

    @Min(0)
    @Max(100)
    private Integer quantity;

    private String description;

    private boolean isauction = false;

    private String photopath="statics/images/no_image.png";

    @Valid
    @ManyToOne(fetch = FetchType.EAGER)//EAGER powoduje zaciągnięcie obiektu VehicleType wraz z obiektem Vehicle.
    @JoinColumn(name="category_id", nullable = false)
    private Category category;

    @Column(name="created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;


    public Item() {
        this.createdDate = new Date();
        this.category = new Category();
    }

    public Item(String name, float price, Category category, Date creationDate, Integer quantity, String description/*, String phtotopath*/) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.createdDate = creationDate;
        this.quantity = quantity;
        this.description = description;
        this.photopath = "statics/images/no_image.png";
    }
}
