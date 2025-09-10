package fiap.gestao.hospitalar.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateConsultaRecordDTO(

        LocalDateTime dataHora,
        UUID pacienteId,

        UUID medicoID,
        String descricao
) {
}
