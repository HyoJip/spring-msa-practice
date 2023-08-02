package me.bbbic.catalogservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import me.bbbic.catalogservice.model.Catalog;
import me.bbbic.catalogservice.repository.CatalogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CatalogService {

  private final CatalogRepository catalogRepository;

  @Transactional(readOnly = true)
  public Catalog findCatalog(String productId) {
    return catalogRepository.findByProductId(productId)
      .orElseThrow(() -> new EntityNotFoundException("Not found Catalog for " + productId));
  }
}
