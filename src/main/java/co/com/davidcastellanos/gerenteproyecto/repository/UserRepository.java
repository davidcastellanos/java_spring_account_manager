package co.com.davidcastellanos.gerenteproyecto.repository;

import co.com.davidcastellanos.gerenteproyecto.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email); //si no existe continue con el programa
}
