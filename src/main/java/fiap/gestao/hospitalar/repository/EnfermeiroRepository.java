package fiap.gestao.hospitalar.repository;

import fiap.gestao.hospitalar.entities.Enfermeiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnfermeiroRepository extends JpaRepository<Enfermeiro, UUID> {
}
