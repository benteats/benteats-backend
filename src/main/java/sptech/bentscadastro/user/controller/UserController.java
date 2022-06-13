package sptech.bentscadastro.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sptech.bentscadastro.data.estructure.Stack;
import sptech.bentscadastro.user.DTO.UserDetailDTO;
import sptech.bentscadastro.user.entity.User;
import sptech.bentscadastro.user.form.UpdatePasswordForm;
import sptech.bentscadastro.user.form.UpdateUserForm;
import sptech.bentscadastro.user.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    Stack stack = new Stack(50);

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    @PostMapping("/registerUser")
    public ResponseEntity<Void> resgisterUser(@RequestBody @Valid User newUser) {
        if (!userRepository.existsByEmail(newUser.getEmail()) && !userRepository.existsByPhone(newUser.getPhone())) {
            newUser.setPassword(getPasswordEncoder().encode(newUser.getPassword()));
            userRepository.save(newUser);
            return ResponseEntity.status(201).build();
        }

        return ResponseEntity.status(403).build();
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(users);
    }

    @PatchMapping("/updateUserById/{idUser}/{field}/{value}")
    public ResponseEntity updateUserById(@PathVariable Integer idUser, @PathVariable String field, @PathVariable String value) {
        if (userRepository.existsById(idUser)) {
            User user = userRepository.findByIdUser(idUser);

            switch (field) {
                case "name":
                    user.setName(value);
                    break;
                case "email":
                    user.setEmail(value);
                    break;
                case "phone":
                    user.setPhone(value);
                    break;
                default:
                    return ResponseEntity.status(406).build();
            }
            userRepository.save(user);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{idUser}")
    public ResponseEntity deleteUserById(@PathVariable Integer idUser) {
        if (userRepository.existsById(idUser)) {
            userRepository.deleteById(idUser);
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/logOffUser/{idUser}")
    public ResponseEntity logOffUser(@PathVariable Integer idUser) {
        if (userRepository.existsById(idUser)) {
            if (userRepository.existsByIdUserAndIsLoggedTrue(idUser)) {
                userRepository.logOff(idUser);
                return ResponseEntity.status(200).build();
            }

            return ResponseEntity.status(406).build();
        }

        return ResponseEntity.status(404).build();
    }

    @PostMapping("/historicStack/{idRestaurant}")
    public ResponseEntity historicStack(@PathVariable int idRestaurant) {
        if (userRepository.existsById(idRestaurant)) {
            stack.push(idRestaurant);
            return ResponseEntity.status(200).body(stack);
        }
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/getHistoricStack")
    public ResponseEntity getHistoricStack() {
        if (stack.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(stack);
    }

    @PatchMapping("/updatePasswordById/{idUser}")
    public ResponseEntity<Void> updatePasswordById(@RequestBody UpdatePasswordForm updateUserForm, @PathVariable Integer idUser) {
        if (userRepository.existsById(idUser)) {
            Optional<User> user = userRepository.findById(idUser);
            if (getPasswordEncoder().matches(updateUserForm.getCurrentPassword(), user.get().getPassword())) {
                String pass =  getPasswordEncoder().encode(updateUserForm.getNewPassword());
                userRepository.updatePasswordById(idUser, pass);
                return ResponseEntity.status(200).build();
            }
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/getUserDetailById/{idUser}")
    public ResponseEntity<UserDetailDTO> getUserDetailById(@PathVariable Integer idUser) {
        if (userRepository.existsById(idUser)) {
            UserDetailDTO userDetail = userRepository.getDetailsById(idUser);
            return ResponseEntity.status(200).body(userDetail);
        }

        return ResponseEntity.status(404).build();
    }

}
