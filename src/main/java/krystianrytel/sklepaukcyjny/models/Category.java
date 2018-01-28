package krystianrytel.sklepaukcyjny.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category{

    @Min(0)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Category(String name){
        this.name = name;
    }
}

