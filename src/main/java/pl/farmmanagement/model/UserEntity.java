package pl.farmmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false, updatable = false)
  private String userName;

  @Column(nullable = false)
  private String password;

  @Column(unique = true, nullable = false)
  private String eMail;

  @Column(nullable = false)
  private String givenName;

  @Column(nullable = false)
  private String surname;
}
