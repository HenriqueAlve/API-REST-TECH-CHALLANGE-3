package fiap.gestao.hospitalar.dto;

public record CreatePacienteRecordDTO(
        String nome,
         String cpf,
         String rg,
         String telefone
) {
}
