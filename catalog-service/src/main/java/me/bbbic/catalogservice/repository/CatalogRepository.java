package me.bbbic.catalogservice.repository;

import me.bbbic.catalogservice.model.Catalog;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CatalogRepository extends CrudRepository<Catalog, Long> {
  Optional<Catalog> findByProductId(String productId);
}
