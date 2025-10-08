package fiap.gestao.hospitalar.service;

import fiap.gestao.hospitalar.dto.CreateMedicoRecordDTO;
import fiap.gestao.hospitalar.dto.UpdateMedicoRecordDTO;
import fiap.gestao.hospitalar.entities.Medico;
import fiap.gestao.hospitalar.exceptions.ResourceNotFoundException;
import fiap.gestao.hospitalar.repository.MedicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository){
        this.medicoRepository = medicoRepository;
    }

    public Medico save(CreateMedicoRecordDTO input) {
        var medico = new Medico();
        medico.setNome(input.nome());
        medico.setCpf(input.cpf());
        medico.setEmail(input.email());
        medico.setTelefone(input.telefone());
        medico.setCrm(input.crm());
        return medicoRepository.save(medico);
    }

    @Transactional
    public void update(UUID id, UpdateMedicoRecordDTO input) {
        var medico = medicoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Médico não encontrado: id=" + id)
        );

        Optional.ofNullable(input.nome()).ifPresent(medico::setNome);
        Optional.ofNullable(input.cpf()).ifPresent(medico::setCpf);
        Optional.ofNullable(input.email()).ifPresent(medico::setEmail);
        Optional.ofNullable(input.telefone()).ifPresent(medico::setTelefone);
        Optional.ofNullable(input.crm()).ifPresent(medico::setCrm);
    }

    public void delete(UUID id) {
        var medico = medicoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Médico não encontrado: id=" + id)
        );
        medicoRepository.delete(medico);
    }

    public List<Medico> findAll() {
        return medicoRepository.findAll();
    }

    public Medico findById(UUID id) {
        return medicoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Médico não encontrado: id=" + id)
        );
    }
}
