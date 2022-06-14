package sptech.bentscadastro.user.securityservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import sptech.bentscadastro.restaurant.repository.RestaurantRepository;
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

    private final RestaurantRepository restaurantRepository;

    public UserDetailServiceIMPL(UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.empty();

//        if(user.get().getUserType().equals("restaurant")) {
//
//        }
        Integer idUser = userRepository.getIdUser(username);
        Integer idRestaurant = restaurantRepository.getIdRestaurantByIdUser(idUser);

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
