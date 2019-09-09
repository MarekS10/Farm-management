package pl.farmmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

}
