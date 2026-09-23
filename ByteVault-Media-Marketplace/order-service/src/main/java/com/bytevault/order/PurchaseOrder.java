package com.bytevault.order;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="purchase_orders") public class PurchaseOrder{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id; @Column(nullable=false) String username; @Column(nullable=false) Long productId; @Column(nullable=false) String productName; @Column(nullable=false) double amount; @Column(nullable=false) String status; @Column(nullable=false) Instant purchasedAt;
 public Long getId(){return id;} public String getUsername(){return username;} public void setUsername(String v){username=v;} public Long getProductId(){return productId;} public void setProductId(Long v){productId=v;} public String getProductName(){return productName;} public void setProductName(String v){productName=v;} public double getAmount(){return amount;} public void setAmount(double v){amount=v;} public String getStatus(){return status;} public void setStatus(String v){status=v;} public Instant getPurchasedAt(){return purchasedAt;} public void setPurchasedAt(Instant v){purchasedAt=v;}
}
