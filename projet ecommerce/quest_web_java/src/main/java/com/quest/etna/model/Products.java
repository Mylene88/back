package com.quest.etna.model;
import javax.persistence.*;

@Entity
@Table(name = "products")
public class Products {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, length = 11)
    private int id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "price")
    private double price;
    
    @Column(name = "id_category")
    private int id_category;
    
    @Column(name = "image")
    private String image;
    
    public Products() {}
    
    public Products(String name, String description, double price, int id_category, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.id_category = id_category;
        this.image = image;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public int getIdCategory() {
        return id_category;
    }
    
    public void setIdCategory(int idCategory) {
        this.id_category = idCategory;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
}
