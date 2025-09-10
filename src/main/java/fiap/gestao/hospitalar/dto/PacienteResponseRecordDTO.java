package fiap.gestao.hospitalar.dto;

import java.util.UUID;

public record PacienteResponseRecordDTO(
        UUID id,
        String nome,
        String cpf,
        String telefone
) {
}
