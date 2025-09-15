package fiap.gestao.hospitalar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateConsultaRecordDTO(

        @NotNull(message = "Data e hora da consulta são obrigatórias.")
        @FutureOrPresent(message = "A data/hora da consulta não pode estar no passado.")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime dataHora,

        @NotNull(message = "O id do paciente é obrigatório.")
        UUID pacienteId,

        @NotNull(message = "O id do medico é obrigatório.")
        UUID medicoID,

        @NotBlank(message = "A descrição é obrigatória.")
        @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres.")
        String descricao
) {
}
