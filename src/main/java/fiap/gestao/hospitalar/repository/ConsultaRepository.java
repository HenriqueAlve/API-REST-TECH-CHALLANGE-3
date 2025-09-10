package fiap.gestao.hospitalar.repository;

import fiap.gestao.hospitalar.entities.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, UUID> {

    List<Consulta> findAllByMedicoId(UUID medicoId);

    UUID id(UUID id);

    List<Consulta> findAllByPacienteId(UUID pacienteId);
}
