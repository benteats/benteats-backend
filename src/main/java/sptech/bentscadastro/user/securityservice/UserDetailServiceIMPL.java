package sptech.bentscadastro.user.securityservice;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import sptech.bentscadastro.user.data.UserDetailData;
import sptech.bentscadastro.user.entity.User;
import sptech.bentscadastro.user.repository.UserRepository;
import sptech.bentscadastro.util.formatt.FormattPhone;

import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;

@Component
public class UserDetailServiceIMPL implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailServiceIMPL(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.empty();
        if (userRepository.existsByEmail(username)) {
            user = userRepository.findByEmail(username);
        }
        try {
            username = FormattPhone.formattPhone(username);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (userRepository.existsByPhone(username)) {
            user = userRepository.findByPhone(username);
        }
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return new UserDetailData(user);
    }
}
