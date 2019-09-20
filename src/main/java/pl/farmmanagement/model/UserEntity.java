package pl.farmmanagement.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String userName;
  private String password;
  private String eMail;
  private String givenName;
  private String surname;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user",
          cascade = CascadeType.ALL)
  private List<FieldEntity> userFields;

  @ManyToMany(fetch = FetchType.EAGER)
  private Set<UserRole> roles;

}
