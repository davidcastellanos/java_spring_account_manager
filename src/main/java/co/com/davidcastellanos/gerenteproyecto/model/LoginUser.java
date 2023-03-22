package co.com.davidcastellanos.gerenteproyecto.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginUser {
    @NotEmpty(message = "El campo de nombre es obligatorio")
    @NotNull(message = "El nombre no puede tener valor nulo")
    @Size(min = 2, max = 30, message = "El nombre debe tener entre 2 y 30 caracteres")
    private String email;

    @NotEmpty(message = "El campo de nombre es obligatorio")
    @NotNull(message = "El password no puede tener valor nulo")
    @Size(min = 7, max = 128, message = "El password debe tener entre 7 y 40 caracteres")
    private String password;

    public LoginUser() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
