package mk.ukim.finki.ib.lab.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String name;
    private int price;
    @ManyToOne
    @JoinColumn(name = "studio_id")
    private User studio;

    public Product(String name, int price, User studio) {
        this.name = name;
        this.price = price;
        this.studio = studio;
    }
}
