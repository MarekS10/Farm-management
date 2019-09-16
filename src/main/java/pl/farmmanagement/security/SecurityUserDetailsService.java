package pl.farmmanagement.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.farmmanagement.model.UserEntity;
import pl.farmmanagement.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserName(login);

        if(user==null){
            throw  new UsernameNotFoundException("Username with login "
                    + login + " not found");
        }

        return new CustomUserDetails(user.getUserName(), user.getPassword(), mapRoles(user), user.getGivenName(), user.getSurname());
    }

    private List<GrantedAuthority> mapRoles(UserEntity user) {
    return user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }
}
