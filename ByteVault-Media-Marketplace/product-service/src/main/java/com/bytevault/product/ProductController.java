package com.bytevault.product;
import jakarta.validation.Valid; import jakarta.validation.constraints.*; import java.util.*; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/products") public class ProductController{
 private final ProductRepository repo; public ProductController(ProductRepository repo){this.repo=repo;}
 @GetMapping public List<Product> all(){return repo.findByActiveTrueOrderByIdAsc();}
 @GetMapping("/{id}") public Product one(@PathVariable Long id){return repo.findById(id).orElseThrow(()->new NoSuchElementException("Product not found"));}
 record ProductRequest(@NotBlank String name,@NotBlank String description,@Positive double price,@NotBlank String type,@NotBlank String assetName){}
 @PostMapping @PreAuthorize("hasRole('ADMIN')") public Product create(@Valid @RequestBody ProductRequest r){Product p=new Product();p.setName(r.name());p.setDescription(r.description());p.setPrice(r.price());p.setType(r.type());p.setAssetName(r.assetName());return repo.save(p);}
 @PutMapping("/{id}") @PreAuthorize("hasRole('ADMIN')") public Product update(@PathVariable Long id,@Valid @RequestBody ProductRequest r){Product p=one(id);p.setName(r.name());p.setDescription(r.description());p.setPrice(r.price());p.setType(r.type());p.setAssetName(r.assetName());return repo.save(p);}
 @DeleteMapping("/{id}") @PreAuthorize("hasRole('ADMIN')") public void delete(@PathVariable Long id){Product p=one(id);p.setActive(false);repo.save(p);}
}
