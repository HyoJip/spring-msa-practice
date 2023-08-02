package me.bbbic.catalogservice.controller.dto;

import me.bbbic.catalogservice.model.Catalog;

import java.time.LocalDate;

public record CatalogDto(
  String productId,
  String productName,
  Integer unitPrice,
  Integer stock,
  LocalDate createdAt
) {
  public static CatalogDto of(Catalog catalog) {
    return new CatalogDto(
      catalog.getProductId(),
      catalog.getProductName(),
      catalog.getUnitPrice(),
      catalog.getStock(),
      catalog.getCreatedAt()
    );
  }
}
