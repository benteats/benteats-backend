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

    @PatchMapping("/updateUserById/{idUser}")
    public ResponseEntity updateUserById(@PathVariable Integer idUser, @RequestBody UpdateUserForm updateUser) {
        if (userRepository.existsById(idUser)) {

            if (updateUser.getName() != null && !updateUser.getName().equals("")) {
                userRepository.updateNameUserById(updateUser.getName(), idUser);
            }

            if (updateUser.getEmail() != null && !updateUser.getEmail().equals("")) {
                userRepository.updateEmailUserById(updateUser.getEmail(), idUser);
            }

            if (updateUser.getPhone() != null && !updateUser.getPhone().equals("")) {
                userRepository.updatePhoneUserById(updateUser.getPhone(), idUser);
            }

            if (updateUser.getCep() != null && !updateUser.getCep().equals("")) {
                userRepository.updateCepUserById(updateUser.getCep(), idUser);
            }

            if (updateUser.getState() != null && !updateUser.getState().equals("")) {
                userRepository.updateStateUserById(updateUser.getState(), idUser);
            }

            if (updateUser.getCity() != null && !updateUser.getCity().equals("")) {
                userRepository.updateCityUserById(updateUser.getCity(), idUser);
            }

            if (updateUser.getDistrict() != null && !updateUser.getDistrict().equals("")) {
                userRepository.updateDistrictUserById(updateUser.getDistrict(), idUser);
            }

            if (updateUser.getAddress() != null && !updateUser.getAddress().equals("")) {
                userRepository.updateAddressUserById(updateUser.getAddress(), idUser);
            }

            if (updateUser.getAddressNumber() != null) {
                userRepository.updateAddressNumberUserById(updateUser.getAddressNumber(), idUser);
            }

            if (updateUser.getLat() != null && !updateUser.getLat().equals("")) {
                userRepository.updateLatUserById(updateUser.getLat(), idUser);
            }

            if (updateUser.getLng() != null && !updateUser.getLng().equals("")) {
                userRepository.updateLatUserById(updateUser.getLng(), idUser);
            }
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


    @PatchMapping("/updatePasswordById/{idUser}")
    public ResponseEntity<Void> updatePasswordById(@RequestBody UpdatePasswordForm updateUserForm, @PathVariable Integer idUser) {
        if (userRepository.existsById(idUser)) {
            Optional<User> user = userRepository.findById(idUser);
            if (getPasswordEncoder().matches(updateUserForm.getCurrentPassword(), user.orElse(new User()).getPassword())) {
                String pass =  getPasswordEncoder().encode(updateUserForm.getNewPassword());
                userRepository.updatePasswordById(idUser, pass);
            }
            return ResponseEntity.status(200).build();
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
