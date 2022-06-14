package sptech.bentscadastro.user.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sptech.bentscadastro.user.entity.User;
import sptech.bentscadastro.user.form.UpdateUserForm;
import sptech.bentscadastro.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = {UserController.class})
class UserControllerTest {

    @Autowired
    UserController controller;

    @MockBean
    UserRepository repository;


    @Test
    @DisplayName("Testa o registro de usuário com um corpo de requisição válido")
    void resgisterUser_validUser() {
        User user1 = new User();
        user1.setPassword("Teste123");

        User user2 = new User();
        user2.setPassword("Senha312@@");

        when(repository.save(user1)).thenReturn(user1);
        when(repository.save(user2)).thenReturn(user2);

        ResponseEntity response1 = controller.resgisterUser(user1);
        assertEquals(HttpStatus.CREATED, response1.getStatusCode());

        ResponseEntity response2 = controller.resgisterUser(user2);
        assertEquals(HttpStatus.CREATED, response2.getStatusCode());
    }


    @Test
    @DisplayName("Retorna o status http 204 quando o não há nenhum usuário registrado na base de dados")
    void getUsers_returnEmptyList() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<User>> response = controller.getUsers();

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Retorna uma lista de usuários com o status http 200")
    void getUsers_returnUserList() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        when(repository.findAll()).thenReturn(List.of(user1, user2, user3));

        ResponseEntity<List<User>> response = controller.getUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(3, response.getBody().size());
        assertEquals(user1, response.getBody().get(0));
    }

//    @Test
//    @DisplayName("Retorna o status 404 se não existir um usuário com o id passado como parametro para o método" +
//            "updateUserById")
//    void updateUserById_invalidId() {
//        Integer id = 1;
//        UpdateUserForm updateUserForm1 = new UpdateUserForm();
//        when(repository.existsById(id)).thenReturn(false);
//
//        ResponseEntity<Void> response = controller.updateUserById(id, updateUserForm1);
//
//        assertEquals(404, response.getStatusCodeValue());
//
//    }
//
//    @Test
//    @DisplayName("Faz o update do usuário quando o id passado como parametro para o metodo" +
//            "updateUserById é válido")
//    void updateUserById_validId() {
//        Integer id = 1;
//        String name = "teste";
//        UpdateUserForm updateUserForm = new UpdateUserForm();
//        when(repository.existsById(id)).thenReturn(true);
//
//        ResponseEntity<Void> response = controller.updateUserById(id, updateUserForm);
//
//        assertEquals(200, response.getStatusCodeValue());
//    }

    @Test
    @DisplayName("Faz o logOff do usuário caso o usuário esteja realmente logado na aplicação " +
            "e o id seja válido")
    void logOffUser_vailidId() {
        Integer id = 1;

        when(repository.existsById(id)).thenReturn(true);
        when(repository.existsByIdUserAndIsLoggedTrue(id)).thenReturn(true);

        ResponseEntity<Void> response = controller.logOffUser(id);
        assertEquals(200, response.getStatusCodeValue());

    }

    @Test
    @DisplayName("retorna o status 406 caso o id informado seja válido e o usuário não esteja logado")
    void logOffUser_IsLoggedFalse() {
        Integer id = 1;
        when(repository.existsById(id)).thenReturn(true);
        when(repository.existsByIdUserAndIsLoggedTrue(id)).thenReturn(false);

        ResponseEntity<Void> response = controller.logOffUser(id);
        assertEquals(406, response.getStatusCodeValue());

    }

    @Test
    @DisplayName("retorna o status 404 caso o id informado não exista na base")
    void logOffUser_invalidId() {
        Integer id = 1;
        when(repository.existsById(id)).thenReturn(false);

        ResponseEntity<Void> response = controller.logOffUser(id);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("apaga um usuário na base caso o id informado seja valido")
    void deleteUserById_validId() {
        Integer id = 1;
        when(repository.existsById(id)).thenReturn(true);

        ResponseEntity<Void> response = controller.deleteUserById(id);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("retorna o status http 404 caso o id informado não exista na base")
    void deleteUserById_invalidId() {
        Integer id = 1;
        when(repository.existsById(id)).thenReturn(false);

        ResponseEntity<Void> response = controller.deleteUserById(id);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Retorna o status 404 caso o id informado não exista na base")
    void authenticateSession_invalidId() {
        Integer id = 1;
        when(repository.existsById(id)).thenReturn(false);

        ResponseEntity<Void> response = controller.deleteUserById(id);

        assertEquals(404, response.getStatusCodeValue());
    }
}