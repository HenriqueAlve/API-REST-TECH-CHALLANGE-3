package fiap.gestao.hospitalar.service;

import fiap.gestao.hospitalar.dto.CreatePacienteRecordDTO;
import fiap.gestao.hospitalar.dto.UpdatePacienteRecordDTo;
import fiap.gestao.hospitalar.entities.Paciente;
import fiap.gestao.hospitalar.exceptions.ResourceNotFoundException;
import fiap.gestao.hospitalar.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente save(CreatePacienteRecordDTO input) {
        Paciente paciente = new Paciente();
        paciente.setNome(input.nome());
        paciente.setCpf(input.cpf());
        paciente.setRg(input.rg());
        paciente.setTelefone(input.telefone());
        return pacienteRepository.save(paciente);
    }

    @Transactional
    public void update(UUID id, UpdatePacienteRecordDTo input) {
        var paciente = pacienteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Paciente não encontrado: id=" + id)
        );

        Optional.ofNullable(input.nome()).ifPresent(paciente::setNome);
        Optional.ofNullable(input.cpf()).ifPresent(paciente::setCpf);
        Optional.ofNullable(input.rg()).ifPresent(paciente::setRg);
        Optional.ofNullable(input.telefone()).ifPresent(paciente::setTelefone);
    }

    public void delete(UUID id) {
        var paciente = pacienteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Paciente não encontrado: id=" + id)
        );
        pacienteRepository.delete(paciente);
    }

    public List<Paciente> findAll() {
        return pacienteRepository.findAll();
    }

    public Paciente findById(UUID id) {
        return pacienteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Paciente não encontrado: id=" + id)
        );
    }
}
