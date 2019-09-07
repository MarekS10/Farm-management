package pl.farmmanagement.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.farmmanagement.dto.UserDTO;
import pl.farmmanagement.model.UserEntity;
import pl.farmmanagement.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final Logger logger = LoggerFactory.getLogger(UserService.class);
  private final UserRepository userRepository;

  public UserDTO add(UserDTO user) {
    UserEntity entity = maptoUser(user);
    userRepository.save(entity);
    logger.info("User {} with id: {} has been added to database", user.getUserName(), user.getId());
    return mapToUserDTO(entity);
  }

  private UserEntity maptoUser(UserDTO a) {
    return UserEntity.builder()
        .id(a.getId())
        .userName(a.getUserName())
        .password(a.getPassword())
        .eMail(a.getEMail())
        .givenName(a.getGivenName())
        .surname(a.getSurname())
        .build();
  }

  private UserDTO mapToUserDTO(UserEntity a) {
    return UserDTO.builder()
        .id(a.getId())
        .userName(a.getUserName())
        .password(a.getPassword())
        .eMail(a.getEMail())
        .givenName(a.getGivenName())
        .surname(a.getSurname())
        .build();
  }
}
