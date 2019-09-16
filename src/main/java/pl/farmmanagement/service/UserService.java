package pl.farmmanagement.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Sets;
import pl.farmmanagement.model.FieldEntity;
import pl.farmmanagement.model.User;
import pl.farmmanagement.model.UserEntity;
import pl.farmmanagement.model.UserRole;
import pl.farmmanagement.repository.RoleRepository;
import pl.farmmanagement.repository.UserRepository;
import pl.farmmanagement.security.SecurityConfig;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {

  private final Logger logger = LoggerFactory.getLogger(UserService.class);
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final SecurityConfig securityConfig;


  public User add(User user) {
    UserEntity entity = maptoUser(user);
    entity.setRoles(roleRepository.findByRole("USER"));
    entity.setPassword(securityConfig.passwordEncoder().encode(entity.getPassword()));
    UserEntity savedUser = userRepository.save(entity);
    logger.info("User {} with id: {} has been added to database", user.getUserName(), user.getId());
    return mapToUserDTO(savedUser);
  }

  public User getByUserName(String userName){
    UserEntity byUserName = userRepository.findByUserNameIgnoreCase(userName);
    return mapToUserDTO(byUserName);
  }

  public Optional<User> getByUserNameAndPassword(String userName, String password){
    Optional<UserEntity> byUserNameAndPassword = userRepository.findByUserNameIgnoreCaseAndPassword(userName, password);
    return byUserNameAndPassword.map(this::mapToUserDTO);
  }

  public List<FieldEntity> getAllUserFieldById(Long userId){
    return userRepository.userFieldsById(userId);
  }



  private UserEntity maptoUser(User a) {
    return UserEntity.builder()
        .id(a.getId())
        .userName(a.getUserName())
        .password(a.getPassword())
        .eMail(a.getEMail())
        .givenName(a.getGivenName())
        .surname(a.getSurname())
        .build();
  }

  private User mapToUserDTO(UserEntity a) {
    return User.builder()
        .id(a.getId())
        .userName(a.getUserName())
        .password(a.getPassword())
        .eMail(a.getEMail())
        .givenName(a.getGivenName())
        .surname(a.getSurname())
        .build();
  }
}
