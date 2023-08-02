package me.bbbic.userservice.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Audit {

  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = false, length = 50, unique = true)
  private String email;

  @Column(nullable = false, unique = true)
  private String password;

  @Builder
  public User(Long id, String name, String email, String password) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
  }

  public void hashPassword(String encode) {
    this.password = encode;
  }
}
