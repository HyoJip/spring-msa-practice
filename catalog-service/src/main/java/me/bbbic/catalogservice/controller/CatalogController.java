package me.bbbic.catalogservice.controller;

import lombok.RequiredArgsConstructor;
import me.bbbic.catalogservice.controller.dto.CatalogDto;
import me.bbbic.catalogservice.model.Catalog;
import me.bbbic.catalogservice.model.CatalogOrder;
import me.bbbic.catalogservice.service.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogController {

  private final CatalogService catalogService;
  @GetMapping("/catalogs/{id}")
  public ResponseEntity<CatalogDto> findCatalog(@PathVariable String id) {

    Catalog catalog = catalogService.findCatalog(id);
    return ResponseEntity.status(200).body(CatalogDto.of(catalog));
  }


}
