package fiap.gestao.hospitalar.service;

import fiap.gestao.hospitalar.dto.CreatePacienteRecordDTO;
import fiap.gestao.hospitalar.dto.UpdatePacienteRecordDTo;
import fiap.gestao.hospitalar.entities.Paciente;
import fiap.gestao.hospitalar.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void update(UUID id, UpdatePacienteRecordDTo input) {
        var paciente = pacienteRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Paciente não encontrado")
        );
        paciente.setNome(input.nome());
        paciente.setCpf(input.cpf());
        paciente.setRg(input.rg());
        paciente.setTelefone(input.telefone());
        pacienteRepository.save(paciente);
    }

    public void delete(UUID id) {
        var paciente = pacienteRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Paciente não encontrado")
        );
        pacienteRepository.delete(paciente);
    }

    public List<Paciente> findAll() {
        return pacienteRepository.findAll();
    }

    public Paciente findById(UUID id) {
        return pacienteRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Paciente nao encontrado")
        );

    }
}
