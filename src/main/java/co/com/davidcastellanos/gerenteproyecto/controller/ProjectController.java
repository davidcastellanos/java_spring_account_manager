package co.com.davidcastellanos.gerenteproyecto.controller;

import co.com.davidcastellanos.gerenteproyecto.model.Project;
import co.com.davidcastellanos.gerenteproyecto.model.User;
import co.com.davidcastellanos.gerenteproyecto.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private AppService service;

    @GetMapping("/new")
    public String newProject(@ModelAttribute("project") Project project,
                             HttpSession session) {
        /* Revisar la sesión*/
        User currentUser = (User) session.getAttribute("userSession");

        if (currentUser == null) {
            return "redirect:/";
        }

        /* Revisar la sesión FIN*/
        return "new.jsp";
    }

    @PostMapping("/create")
    public String createProject(@Valid @ModelAttribute("project") Project project,
                                BindingResult result,
                                HttpSession session) {

        /* Revisar la sesión*/
        User currentUser = (User) session.getAttribute("userSession");

        if (currentUser == null) {
            return "redirect:/";
        }

        /* Revisar la sesión FIN*/

        //REVISAR ERRORES DEL REGISTRO
        if (result.hasErrors()) {
            return "new.jsp";
        } else {
            //guardamos el projecto en variable
            Project nuevoProyecto = service.saveProject(project);
            //agregar a la lista de proyectos a los que me uní
            User myUser = service.findUserByID(currentUser.getId()); // identificar usuario a adicionar proyecti
            myUser.getProjectsJoined().add(nuevoProyecto); // se agrega proyecto a usuario
            service.saveUser(myUser);

            return "redirect:/dashboard";
        }

    }

    @GetMapping("/join/{projectId}")
    public String join(@PathVariable("id") Long projectId,
                       HttpSession session) {
        /* Revisar la sesión*/
        User currentUser = (User) session.getAttribute("userSession");

        if (currentUser == null) {
            return "redirect:/";
        }
        /* Revisar la sesión FIN*/

        /*MÉTODO EN EL SERVICE QUE NOS UNA AL PROYECTO*/
        service.saveProjectUser(projectId, currentUser.getId());
        return "redirect:/dashboard";

    }

    @GetMapping("/leave/{projectId}")
    public String leave(@PathVariable("projectId") Long projectId,
                       HttpSession session) {

        /* Revisar la sesión*/
        User currentUser = (User) session.getAttribute("userSession");

        if (currentUser == null) {
            return "redirect:/";
        }
        /* Revisar la sesión FIN*/

        /*MÉTODO EN EL SERVICE QUE NOS ELIMINE DE UN PROYECTO*/
        service.removeProjectUser(projectId, currentUser.getId());
        return "redirect:/dashboard";
    }

    @GetMapping("/edit/{projectId}")
    public String edit(@PathVariable("projectId") Long projectId,
                        HttpSession session,
                       @ModelAttribute("project") Project project,
                       Model model) {

        /* Revisar la sesión*/
        User currentUser = (User) session.getAttribute("userSession");

        if (currentUser == null) {
            return "redirect:/";
        }
        /* Revisar la sesión FIN*/

        Project projectEdit = service.findProjectByID(projectId);

        /*PENIDIENTE: REVISIÓN DE QUE ID DE LEAD COINCIDA CON EL DE LA SESIÓN*/
        if (currentUser.getId()!= projectEdit.getLead().getId()) {
            return "redirect:/";
        }

        model.addAttribute("project", projectEdit);

        return "edit.jsp";

    }

    @PutMapping("/update")
    public String update(@Valid @ModelAttribute("project") Project project,
                         BindingResult result,
                         HttpSession session) {
        /* Revisar la sesión*/
        User currentUser = (User) session.getAttribute("userSession");

        if (currentUser == null) {
            return "redirect:/";
        }
        /* Revisar la sesión FIN*/

        if (result.hasErrors()){
            return "edit.jsp";
        } else {
            // los usuarios que forman parte del proyecto se agreguen de nuevo
            Project thisProject = service.findProjectByID(project.getId());
            List<User> usersJoinedProject = thisProject.getUsersJoined();

            project.setUsersJoined(usersJoinedProject);
            service.saveProject(project);

            return  "redirect:/dashboard";
        }
    }

}
