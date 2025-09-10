package fiap.gestao.hospitalar.dto;

public record UpdatePacienteRecordDTo(
        String nome,
        String cpf,
        String rg,
        String telefone
) {
}
