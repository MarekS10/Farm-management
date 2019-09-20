package pl.farmmanagement.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserNameIgnoreCase(login);

        if(user==null){
            throw  new UsernameNotFoundException("Username with login "
                    + login + " not found");
        }

        return new LoggedUserDetails(user.getUserName(), user.getPassword(), mapRoles(user),user.getId());
    }

    private List<GrantedAuthority> mapRoles(UserEntity user) {
    return user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole())).collect(Collectors.toList());
    }
}
