package com.bytevault.product;
import jakarta.persistence.*;
@Entity @Table(name="products") public class Product{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id; @Column(nullable=false) String name; @Column(length=2000) String description; @Column(nullable=false) double price; @Column(nullable=false) String type; @Column(nullable=false) String assetName; @Column(nullable=false) boolean active=true;
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;} public double getPrice(){return price;} public void setPrice(double v){price=v;} public String getType(){return type;} public void setType(String v){type=v;} public String getAssetName(){return assetName;} public void setAssetName(String v){assetName=v;} public boolean isActive(){return active;} public void setActive(boolean v){active=v;}
}
