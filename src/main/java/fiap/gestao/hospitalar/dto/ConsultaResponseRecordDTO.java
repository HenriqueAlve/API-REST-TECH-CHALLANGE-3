package fiap.gestao.hospitalar.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConsultaResponseRecordDTO(
        UUID id,
        LocalDateTime dataHora,
        PacienteResponseRecordDTO paciente,
        MedicoResponseRecordDTO medico,
        String descricao
) {
}
