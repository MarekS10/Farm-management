package pl.farmmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@PasswordValid
public class User {

  private Long id;

  @NotNull(message = "Required")
  @Size(min = 3, message = "Minimum 3 characters")
  @UniqueUserName
  private String userName;

  @Pattern(
      regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
      message = "Minimum eight characters, at least one letter and one number")
  private String password;


  private String rePassword;

  @NotNull(message = "Required")
  @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "Invalid email")
  private String eMail;

  @NotNull(message = "Required")
  @Size(min = 2, message = "Minimum 2 characters")
  private String givenName;

  @NotNull(message = "Required")
  @Size(min = 2, message = "Minimum 2 characters")
  private String surname;
}
