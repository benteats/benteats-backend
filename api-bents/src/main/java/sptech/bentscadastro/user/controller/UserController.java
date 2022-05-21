package sptech.bentscadastro.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.bentscadastro.user.entity.User;
import sptech.bentscadastro.user.form.LoginUserForm;
import sptech.bentscadastro.user.form.UpdateUserForm;
import sptech.bentscadastro.user.repository.UserRepository;
import sptech.bentscadastro.util.formatt.FormattPhone;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity resgisterUser(@RequestBody User newUser) {
        System.out.println(newUser.getEmail());
        userRepository.save(newUser);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        System.out.println(users);
        return ResponseEntity.status(200).body(users);
    }

    @PatchMapping("/updateUserById/{idUser}")
    public ResponseEntity updateUserById(@PathVariable Integer idUser, @RequestBody UpdateUserForm updateUser) {
        ResponseEntity result = ResponseEntity.status(404).build();
        if (userRepository.existsById(idUser)) {

            if (updateUser.getName() != null && !updateUser.getName().equals("")) {
                userRepository.updateNameUserById(updateUser.getName(), idUser);
                result = ResponseEntity.status(200).build();
            }

            if (updateUser.getEmail() != null && !updateUser.getEmail().equals("")) {
                userRepository.updateEmailUserById(updateUser.getEmail(), idUser);
                result = ResponseEntity.status(200).build();
            }

            if (updateUser.getPhone() != null && !updateUser.getPhone().equals("")) {
                userRepository.updatePhoneUserById(updateUser.getPhone(), idUser);
                result = ResponseEntity.status(200).build();
            }

            if (updateUser.getCep() != null && !updateUser.getCep().equals("")) {
                userRepository.updateCepUserById(updateUser.getCep(), idUser);
                result = ResponseEntity.status(200).build();
            }

            if (updateUser.getState() != null && !updateUser.getState().equals("")) {
                userRepository.updateStateUserById(updateUser.getState(), idUser);
                result = ResponseEntity.status(200).build();
            }

            if (updateUser.getCity() != null && !updateUser.getCity().equals("")) {
                userRepository.updateCityUserById(updateUser.getCity(), idUser);
                result = ResponseEntity.status(200).build();
            }

            if (updateUser.getDistrict() != null && !updateUser.getDistrict().equals("")) {
                userRepository.updateDistrictUserById(updateUser.getDistrict(), idUser);
                result = ResponseEntity.status(200).build();
            }

            if (updateUser.getAddress() != null && !updateUser.getAddress().equals("")) {
                userRepository.updateAddressUserById(updateUser.getAddress(), idUser);
                result = ResponseEntity.status(200).build();
            }

            if (updateUser.getAddressNumber() != null) {
                userRepository.updateAddressNumberUserById(updateUser.getAddressNumber(), idUser);
                result = ResponseEntity.status(200).build();
            }

            if (updateUser.getLat() != null && !updateUser.getLat().equals("")) {
                userRepository.updateLatUserById(updateUser.getLat(), idUser);
                result = ResponseEntity.status(200).build();
            }

            if (updateUser.getLng() != null && !updateUser.getLng().equals("")) {
                userRepository.updateLatUserById(updateUser.getLng(), idUser);
                result = ResponseEntity.status(200).build();
            }
        }
        return result;
    }

    @DeleteMapping("/{idUser}")
    public ResponseEntity deleteUserById(@PathVariable Integer idUser) {
        if (userRepository.existsById(idUser)) {
            userRepository.deleteById(idUser);
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(404).build();
    }

    @PostMapping("/loginUser")
    public ResponseEntity loginUser(@RequestBody LoginUserForm loginUser) {
        if (userRepository.existsByEmailAndPassword(loginUser.getLogin(), loginUser.getPassword())) {
            userRepository.loginUser(loginUser.getLogin(), loginUser.getPassword());
            Integer idUser = userRepository.getIdUser(loginUser.getLogin());
            return ResponseEntity.status(200).body(idUser);
        }

        try {
            loginUser.setLogin(FormattPhone.formattPhone(loginUser.getLogin()));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        if (userRepository.existsByPhoneAndPassword(loginUser.getLogin(), loginUser.getPassword())) {
            userRepository.loginUser(loginUser.getLogin(), loginUser.getPassword());
            Integer idUser = userRepository.getIdUser(loginUser.getLogin());
            return ResponseEntity.status(200).body(idUser);
        }

        return ResponseEntity.status(404).build();
    }

    public ResponseEntity logOffUser(@PathVariable Integer idUser) {
        if (userRepository.existsById(idUser)) {
            if (userRepository.existsByIdUserAndIsLoggedTrue(idUser)) {
                userRepository.logOff(idUser);
                return ResponseEntity.status(200).build();
            }

            if (userRepository.existsByIdUserAndIsLoggedFalse(idUser))
                return ResponseEntity.status(406).build();
        }

        return ResponseEntity.status(404).build();
    }

    @GetMapping("/authenticateSession/{idUser}")
    public ResponseEntity<Boolean> authenticateSession(@PathVariable Integer idUser) {

        if (userRepository.existsById(idUser)) {
            if (userRepository.existsByIdUserAndIsLoggedTrue(idUser)) {
                return ResponseEntity.status(200).body(true);
            }

            if (userRepository.existsByIdUserAndIsLoggedFalse(idUser)) {
                return ResponseEntity.status(200).body(false);
            }
        }

        return ResponseEntity.status(404).build();
    }

}
