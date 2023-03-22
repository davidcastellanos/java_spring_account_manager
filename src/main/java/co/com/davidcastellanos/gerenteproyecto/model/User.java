package co.com.davidcastellanos.gerenteproyecto.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El campo de nombre es obligatorio")
    @NotNull(message = "El nombre no puede tener valor nulo")
    @Size(min = 2, max = 30, message = "El nombre debe tener entre 2 y 30 caracteres")
    private String firstName;

    @NotEmpty(message = "El campo de apellido es obligatorio")
    @NotNull(message = "El apellido no puede tener valor nulo")
    @Size(min = 2, max = 30, message = "El apellido debe tener entre 2 y 30 caracteres")
    private String lastName;

    @NotEmpty(message = "El campo de nombre es obligatorio")
    @NotNull(message = "El email no puede tener valor nulo")
    @Size(min = 2, max = 30, message = "El email debe tener entre 2 y 30 caracteres")
    @Email(message = "Ingrese un correo válido")
    private String email;

    @NotEmpty(message = "El campo de nombre es obligatorio")
    @NotNull(message = "El password no puede tener valor nulo")
    @Size(min = 7, max = 128, message = "El password debe tener entre 7 y 40 caracteres")
    private String password;

    @Transient //No guardar el atributo en DB
    @NotEmpty(message = "El campo de nombre es obligatorio")
    @NotNull(message = "El nombre no puede tener valor nulo")
    @Size(min = 7, max = 128, message = "El password debe tener entre 7 y 40 caracteres")
    private String confirmPassword;

    @Column(updatable = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;


    // un lead puede crear muchos proyectos
    @OneToMany(mappedBy = "lead", fetch = FetchType.LAZY)
    private List<Project> myProjects;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "projects_has_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> projectsJoined;

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirm) {
        this.confirmPassword = confirm;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }




    public List<Project> getMyProjects() {
        return myProjects;
    }

    public void setMyProjects(List<Project> myProjects) {
        this.myProjects = myProjects;
    }

    public List<Project> getProjectsJoined() {
        return projectsJoined;
    }

    public void setProjectsJoined(List<Project> projectsJoined) {
        this.projectsJoined = projectsJoined;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }


    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
}
