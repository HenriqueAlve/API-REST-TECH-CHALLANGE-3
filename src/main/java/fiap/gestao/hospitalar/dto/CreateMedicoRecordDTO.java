package fiap.gestao.hospitalar.dto;

public record CreateMedicoRecordDTO(
        String nome,
         String telefone,
         String email,
         String cpf,
         String crm
) {
}
