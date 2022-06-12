package sptech.bentscadastro.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sptech.bentscadastro.user.DTO.UserDetailDTO;
import sptech.bentscadastro.user.entity.User;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Modifying
    @Query("update User u set u.name = ?1 where u.idUser = ?2")
    void updateNameUserById(String value, Integer idUser);

    @Transactional
    @Modifying
    @Query("update User u set u.email = ?1 where u.idUser = ?2")
    void updateEmailUserById(String value, Integer idUser);

    @Transactional
    @Modifying
    @Query("update User u set u.phone = ?1 where u.idUser = ?2")
    void updatePhoneUserById(String value, Integer idUser);

    @Transactional
    @Modifying
    @Query("update User u set u.cep = ?1 where u.idUser = ?2")
    void updateCepUserById(String value, Integer idUser);

    @Transactional
    @Modifying
    @Query("update User u set u.state = ?1 where u.idUser = ?2")
    void updateStateUserById(String value, Integer idUser);

    @Transactional
    @Modifying
    @Query("update User u set u.city = ?1 where u.idUser = ?2")
    void updateCityUserById(String value, Integer idUser);

    @Transactional
    @Modifying
    @Query("update User u set u.district = ?1 where u.idUser = ?2")
    void updateDistrictUserById(String value, Integer idUser);

    @Transactional
    @Modifying
    @Query("update User u set u.address = ?1 where u.idUser = ?2")
    void updateAddressUserById(String value, Integer idUser);

    @Transactional
    @Modifying
    @Query("update User u set u.addressNumber = ?1 where u.idUser = ?2")
    void updateAddressNumberUserById(String value, Integer idUser);

    @Transactional
    @Modifying
    @Query("update User u set u.lat = ?1 where u.idUser = ?2")
    void updateLatUserById(String value, Integer idUser);

    @Transactional
    @Modifying
    @Query("update User u set u.lng = ?1 where u.idUser = ?2")
    void updateLngUserById(String value, Integer idUser);

    boolean existsByEmailAndPassword(String email, String password);

    boolean existsByPhoneAndPassword(String phone, String password);

    @Transactional
    @Modifying
    @Query("update User u set u.isLogged = true where u.email = ?1 or u.phone = ?1")
    void loginUser(String login);

    @Transactional
    @Modifying
    @Query("update User u set u.isLogged = false where u.idUser = ?1")
    void logOff(Integer idUser);

    @Transactional
    @Query("select u.idUser from User u where u.email = ?1 or u.phone = ?1")
    Integer getIdUser(String login);

//    @Transactional
//    @Query("select u.phone from User u where u.idUser = ?1")
//    Integer getPhoneUserById(Long id);

    boolean existsByIdUserAndIsLoggedTrue(Integer idUser);
    boolean existsByIdUserAndIsLoggedFalse(Integer idUser);

    User findByIdUser(Integer idUser);

    boolean existsByEmail(String login);

    boolean existsByPhone(String login);

//    User findByEmail(String login);

//    User findByPhone(String login);

    Optional<User> findByEmail(String username);

    Optional<User> findByPhone(String username);

    @Transactional
    @Modifying
    @Query("update User u set u.password = ?2 where u.idUser = ?1")
    void updatePasswordById(Integer idUser, String newPassword);

    @Query("select new sptech.bentscadastro.user.DTO.UserDetailDTO(u.name, u.email, u.phone, u.password) from User u where u.idUser = ?1")
    UserDetailDTO getDetailsById(Integer idUser);
}
