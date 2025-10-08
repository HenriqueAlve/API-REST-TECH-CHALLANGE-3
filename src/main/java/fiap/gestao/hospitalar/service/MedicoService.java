package fiap.gestao.hospitalar.service;

import fiap.gestao.hospitalar.dto.CreateMedicoRecordDTO;
import fiap.gestao.hospitalar.dto.UpdateMedicoRecordDTO;
import fiap.gestao.hospitalar.entities.Medico;
import fiap.gestao.hospitalar.repository.MedicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
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
                () -> new RuntimeException("Médico com o" + "{id}" +"não encontrado")
        );
        if (input.nome() != null) {
            medico.setNome(input.nome());
        }

        if (input.cpf() != null) {
            medico.setCpf(input.cpf());
        }

        if (input.email() != null) {
            medico.setEmail(input.email());
        }

        if (input.telefone() != null) {
            medico.setTelefone(input.telefone());
        }

        if (input.crm() != null) {
            medico.setCrm(input.crm());
        }

    }

    public void delete(UUID id) {
        var medico = medicoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Medico com o" + id + "não encontrado")
        );
        medicoRepository.delete(medico);
    }

    public List<Medico> findAll() {
        return medicoRepository.findAll();
    }

    public Medico findById(UUID id) {
        return  medicoRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Medico com o " + id + "não encontrado")
        );

    }
}
