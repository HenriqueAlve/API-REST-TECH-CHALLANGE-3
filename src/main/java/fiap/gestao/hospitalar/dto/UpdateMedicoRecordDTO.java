package fiap.gestao.hospitalar.dto;

public record UpdateMedicoRecordDTO(
        String nome,
        String telefone,
        String email,
        String cpf,
        String crm
) {
}
