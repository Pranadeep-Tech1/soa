package com.bytevault.order;
import java.nio.charset.StandardCharsets; import java.time.Instant; import java.util.*;
import jakarta.validation.Valid; import jakarta.validation.constraints.NotNull; import org.springframework.beans.factory.annotation.Value; import org.springframework.http.*; import org.springframework.security.oauth2.jwt.Jwt; import org.springframework.security.core.annotation.AuthenticationPrincipal; import org.springframework.web.bind.annotation.*; import org.springframework.web.reactive.function.client.WebClient;
@RestController @RequestMapping("/api/orders") public class OrderController{
 private final OrderRepository orders; private final WebClient.Builder web; private final DownloadTokenService downloadTokens; private final String gatewayBase;
 public OrderController(OrderRepository orders,WebClient.Builder web,DownloadTokenService downloadTokens,@Value("${app.gateway-base-url}")String gatewayBase){this.orders=orders;this.web=web;this.downloadTokens=downloadTokens;this.gatewayBase=gatewayBase;}
 record PurchaseRequest(@NotNull Long productId){}
 record ProductDto(Long id,String name,String description,double price,String type,String assetName,boolean active){}
 record PurchaseResponse(Long orderId,String status,String productName,double amount,String downloadUrl,Instant purchasedAt){}
 @PostMapping("/purchase") public PurchaseResponse purchase(@Valid @RequestBody PurchaseRequest req,@AuthenticationPrincipal Jwt jwt,@RequestHeader(name="Authorization",required=false)String authorization){
   ProductDto p=web.build().get().uri("http://PRODUCT-SERVICE/api/products/{id}",req.productId()).headers(h->{if(authorization!=null)h.set(HttpHeaders.AUTHORIZATION,authorization);}).retrieve().bodyToMono(ProductDto.class).block();
   if(p==null||!p.active()) throw new NoSuchElementException("Product unavailable");
   PurchaseOrder o=new PurchaseOrder();o.setUsername(jwt.getSubject());o.setProductId(p.id());o.setProductName(p.name());o.setAmount(p.price());o.setStatus("PAID");o.setPurchasedAt(Instant.now());o=orders.save(o);
   String token=downloadTokens.create(o.getId(),o.getProductId(),Instant.now().plusSeconds(900));
   String url=gatewayBase+"/api/orders/download/"+o.getId()+"?token="+token;
   return new PurchaseResponse(o.getId(),o.getStatus(),o.getProductName(),o.getAmount(),url,o.getPurchasedAt());
 }
 @GetMapping("/my") public List<PurchaseOrder> my(@AuthenticationPrincipal Jwt jwt){return orders.findByUsernameOrderByPurchasedAtDesc(jwt.getSubject());}
 @GetMapping("/download/{orderId}") public ResponseEntity<byte[]> download(@PathVariable Long orderId,@RequestParam String token){
   PurchaseOrder o=orders.findById(orderId).orElseThrow(()->new NoSuchElementException("Order not found"));
   if(!downloadTokens.verify(token,o.getId(),o.getProductId())) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
   String content="ByteVault secure digital asset\n\nAsset: "+o.getProductName()+"\nOrder: "+o.getId()+"\nThis demo endpoint represents the purchased e-book/software file.";
   return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + safe(o.getProductName()) + "-download.txt\"").body(content.getBytes(StandardCharsets.UTF_8));
 }
 private String safe(String s){return s.replaceAll("[^a-zA-Z0-9-_]","_");}
}
