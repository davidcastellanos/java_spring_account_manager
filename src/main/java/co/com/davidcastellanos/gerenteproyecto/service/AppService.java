package co.com.davidcastellanos.gerenteproyecto.service;

import co.com.davidcastellanos.gerenteproyecto.model.LoginUser;
import co.com.davidcastellanos.gerenteproyecto.model.Project;
import co.com.davidcastellanos.gerenteproyecto.model.User;
import co.com.davidcastellanos.gerenteproyecto.repository.ProjectRepository;
import co.com.davidcastellanos.gerenteproyecto.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
public class AppService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    //función que registra un usuario
    public User registerUser(User nuevoUser, BindingResult result) {
        //verificar si el password y su confirmación existen
        if (!nuevoUser.getPassword().equals(nuevoUser.getConfirmPassword())) {
            result.rejectValue("password", "Matches", "Las contraseñas no coinciden");

        }

        //revisar si el mail ya existe
        String newEmail = nuevoUser.getEmail();
        if (userRepository.findByEmail(newEmail).isPresent()) {
            result.rejectValue("email", "Unique", "El email fue ingresado previamente");
        }

        if (result.hasErrors()) {
            return null;
        } else {
            // Encriptar la contraseña
            String passWordEncrypt = BCrypt.hashpw(nuevoUser.getPassword(), BCrypt.gensalt());
            nuevoUser.setPassword(passWordEncrypt);
            return userRepository.save(nuevoUser);
        }
    }

    public User loginUser(LoginUser newLogin, BindingResult result) {

        Optional<User> posibleUsuario = userRepository.findByEmail(newLogin.getEmail());

        if (!posibleUsuario.isPresent()) {
            result.rejectValue("email", "Unique", "Correo NO registrado");
            return null;
        }

        User userLogin = posibleUsuario.get();
        if (!BCrypt.checkpw(newLogin.getPassword(), userLogin.getPassword())){
            result.rejectValue("password", "Matches", "La contraseña ingresada no coincide");
        }

        if (result.hasErrors()) {
            return null;
        } else {
            return userLogin;
        }

    }

    /*fUNCIÓN PARA GUARDAR OBJETO pROYECTO EN DB*/
    public Project saveProject(Project nuevoProjecto) {
        return projectRepository.save(nuevoProjecto);
    }

    /*Regrese objeto de usuario en base de su ID*/
    public User findUserByID(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /*Guarda en DB los cambios realizados en usuario*/
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // lista de proyectos a los cuales NO pertenezco
    public List<Project> finOtherProjects(User userInSession) {
        return projectRepository.findByUsersJoinedNotContains(userInSession);

    }

    // lista de proyectos a los cuales SI pertenezco
    public List<Project> findMyProjects(User userInSession) {
        return projectRepository.findAllByUsersJoined(userInSession);
    }

    // Proyecto por ID
    public  Project findProjectByID(Long id) {
        return projectRepository.findById(id).orElse(null);
    }


    // Método que nos une a un proyecto
    public void saveProjectUser(Long projectId, Long userId) {
        User myUser = findUserByID(userId);
        Project myProject = findProjectByID(projectId);

        myUser.getProjectsJoined().add(myProject);
        userRepository.save(myUser);
    }

    // Método que nos ELIMINE a un proyecto
    public void removeProjectUser(Long projectId, Long userId) {
        User myUser = findUserByID(userId);
        Project myProject = findProjectByID(projectId);

        myUser.getProjectsJoined().remove(myProject);
        userRepository.save(myUser);


    }

}
