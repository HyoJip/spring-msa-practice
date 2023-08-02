package me.bbbic.catalogservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table(name = "catalogs")
@ToString @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Catalog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 120, unique = true)
  private String productId;

  @Column(nullable = false)
  private String productName;

  @Column(nullable = false)
  private Integer stock;

  @Column(nullable = false)
  private Integer unitPrice;

  @CreatedDate
  @ColumnDefault("CURRENT_TIMESTAMP")
  private LocalDate createdAt;

  @Builder
  public Catalog(Long id, String productId, String productName, Integer stock, Integer unitPrice, LocalDate createdAt) {
    this.id = id;
    this.productId = productId;
    this.productName = productName;
    this.stock = stock;
    this.unitPrice = unitPrice;
    this.createdAt = createdAt;
  }
}
