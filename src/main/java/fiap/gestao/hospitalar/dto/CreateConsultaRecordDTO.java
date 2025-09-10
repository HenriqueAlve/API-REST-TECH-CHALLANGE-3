package fiap.gestao.hospitalar.dto;

import fiap.gestao.hospitalar.entities.Medico;
import fiap.gestao.hospitalar.entities.Paciente;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateConsultaRecordDTO(

        LocalDateTime dataHora,
        UUID pacienteId,

 UUID medicoId,
 String descricao
) {
}
