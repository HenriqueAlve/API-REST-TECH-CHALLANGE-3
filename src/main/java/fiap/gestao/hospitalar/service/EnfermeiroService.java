package fiap.gestao.hospitalar.service;

import fiap.gestao.hospitalar.dto.CreateEnfermeiroRecordDTO;
import fiap.gestao.hospitalar.dto.UpdateEnfermeiroRecordDTO;
import fiap.gestao.hospitalar.entities.Enfermeiro;
import fiap.gestao.hospitalar.exceptions.ResourceNotFoundException;
import fiap.gestao.hospitalar.repository.EnfermeiroRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EnfermeiroService {

    private final EnfermeiroRepository enfermeiroRepository;

    public EnfermeiroService(EnfermeiroRepository enfermeiroRepository) {
        this.enfermeiroRepository = enfermeiroRepository;
    }

    public Enfermeiro save(CreateEnfermeiroRecordDTO input) {
        Enfermeiro enfermeiro = new Enfermeiro();
        enfermeiro.setNome(input.nome());
        enfermeiro.setEmail(input.email());
        enfermeiro.setTelefone(input.telefone());
        return enfermeiroRepository.save(enfermeiro);
    }

    @Transactional
    public void update(UUID id, UpdateEnfermeiroRecordDTO input) {
        var enfermeiro = enfermeiroRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Enfermeiro não encontrado: id=" + id)
        );

        Optional.ofNullable(input.nome()).ifPresent(enfermeiro::setNome);
        Optional.ofNullable(input.email()).ifPresent(enfermeiro::setEmail);
        Optional.ofNullable(input.telefone()).ifPresent(enfermeiro::setTelefone);
    }

    public void delete(UUID id) {
        var enfermeiro = enfermeiroRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Enfermeiro não encontrado: id=" + id)
        );
        enfermeiroRepository.delete(enfermeiro);
    }

    public List<Enfermeiro> findAll() {
        return enfermeiroRepository.findAll();
    }

    public Enfermeiro findById(UUID id) {
        return enfermeiroRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Enfermeiro não encontrado: id=" + id)
        );
    }
}
