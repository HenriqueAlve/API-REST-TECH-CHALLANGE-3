package fiap.gestao.hospitalar.repository;

import fiap.gestao.hospitalar.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User  findByLogin(String login);
}
