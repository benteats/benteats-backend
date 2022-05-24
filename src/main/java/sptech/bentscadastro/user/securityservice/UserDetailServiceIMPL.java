package sptech.bentscadastro.user.securityservice;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import sptech.bentscadastro.user.data.UserDetailData;
import sptech.bentscadastro.user.entity.User;
import sptech.bentscadastro.user.repository.UserRepository;

import java.util.Optional;

@Component
public class UserDetailServiceIMPL implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailServiceIMPL(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        return new UserDetailData(user);
    }
}
