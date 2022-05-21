package sptech.bentscadastro.user.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class LoginUserForm {
    @NotBlank
    private String login;

    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9]{3})(?=.*[A-z]{1})[A-z0-9]{6,}$", message = "Senha inválida")
    private String password;



    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
