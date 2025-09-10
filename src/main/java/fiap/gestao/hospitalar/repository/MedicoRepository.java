package fiap.gestao.hospitalar.repository;

import fiap.gestao.hospitalar.entities.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@Repository
public interface MedicoRepository extends JpaRepository<Medico, UUID> {
    UUID id(UUID id);
}
