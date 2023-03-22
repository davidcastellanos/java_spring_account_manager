package co.com.davidcastellanos.gerenteproyecto.repository;

import co.com.davidcastellanos.gerenteproyecto.model.Project;
import co.com.davidcastellanos.gerenteproyecto.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    //Seleccionamos la lista de proyectos a los que el usuario NO pertenece
    List<Project> findByUsersJoinedNotContains(User user);


    //Seleccionamos la lista de proyectos a los que los usuarios SI pertenecen
    List<Project> findAllByUsersJoined(User user);

   //


}
