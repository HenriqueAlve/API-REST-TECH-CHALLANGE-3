package fiap.gestao.hospitalar.dto;

import java.util.UUID;

public record MedicoResponseRecordDTO(
        UUID id,
        String nome,
        String crm
) {
}
