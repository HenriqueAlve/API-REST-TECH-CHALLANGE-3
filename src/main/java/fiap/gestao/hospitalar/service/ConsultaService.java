package fiap.gestao.hospitalar.service;

import fiap.gestao.hospitalar.dto.*;
import fiap.gestao.hospitalar.entities.Consulta;
import fiap.gestao.hospitalar.exceptions.ResourceNotFoundException;
import fiap.gestao.hospitalar.repository.ConsultaRepository;
import fiap.gestao.hospitalar.repository.MedicoRepository;
import fiap.gestao.hospitalar.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final ConsultaPublisherService consultaPublisherService;

    public ConsultaService(ConsultaRepository consultaRepository,
                           MedicoRepository medicoRepository,
                           PacienteRepository pacienteRepository,
                           ConsultaPublisherService consultaPublisherService) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.consultaPublisherService = consultaPublisherService;
    }


    public Consulta save(CreateConsultaRecordDTO input) {
        var medico = medicoRepository.findById(input.medicoId()).orElseThrow(
                () -> new ResourceNotFoundException("Médico não encontrado: id=" + input.medicoId())
        );
        var paciente = pacienteRepository.findById(input.pacienteId()).orElseThrow(
                () -> new ResourceNotFoundException("Paciente não encontrado: id=" + input.pacienteId())
        );

        var consulta = new Consulta();
        consulta.setMedico(medico);
        consulta.setPaciente(paciente);
        consulta.setDataHora(input.dataHora());
        consulta.setDescricao(input.descricao());

        var saved = consultaRepository.save(consulta);
        consultaPublisherService.sendNewConsulta(input);
        return saved;
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponseRecordDTO> listarPorMedico(UUID medicoId) {
        if (!medicoRepository.existsById(medicoId)) {
            throw new ResourceNotFoundException("Médico não encontrado: id=" + medicoId);
        }

        var consultas = consultaRepository.findAllByMedicoId(medicoId);

        return consultas.stream()
                .map(c -> new ConsultaResponseRecordDTO(
                        c.getId(),
                        c.getDataHora(),
                        new PacienteResponseRecordDTO(
                                c.getPaciente().getId(),
                                c.getPaciente().getNome(),
                                c.getPaciente().getCpf(),
                                c.getPaciente().getTelefone()
                        ),
                        new MedicoResponseRecordDTO(
                                c.getMedico().getId(),
                                c.getMedico().getNome(),
                                c.getMedico().getCrm()
                        ),
                        c.getDescricao()
                ))
                .toList();
    }

    @Transactional
    public void update(UUID id, UpdateConsultaRecordDTO input) {
        var consulta = consultaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Consulta não encontrada: id=" + id)
        );

        if (input.dataHora() != null){
            consulta.setDataHora(input.dataHora());
        }

        if (input.descricao() != null){
            consulta.setDescricao(input.descricao());
        }

    }

    @Transactional(readOnly = true)
    public List<ConsultaResponseRecordDTO> findAllConsultas() {
        return consultaRepository.findAll()
                .stream()
                .map(consulta -> new ConsultaResponseRecordDTO(
                        consulta.getId(),
                        consulta.getDataHora(),
                        new PacienteResponseRecordDTO(
                                consulta.getPaciente().getId(),
                                consulta.getPaciente().getNome(),
                                consulta.getPaciente().getCpf(),
                                consulta.getPaciente().getTelefone()
                        ),
                        new MedicoResponseRecordDTO(
                                consulta.getMedico().getId(),
                                consulta.getMedico().getNome(),
                                consulta.getMedico().getCrm()
                        ),
                        consulta.getDescricao()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponseRecordDTO> listarConsultaPorPaciente(UUID pacienteId) {
        // Opcional: garantir que o paciente exista
        if (!pacienteRepository.existsById(pacienteId)) {
            throw new ResourceNotFoundException("Paciente não encontrado: id=" + pacienteId);
        }

        var consultas = consultaRepository.findAllByPacienteId(pacienteId);

        return consultas.stream()
                .map(c -> new ConsultaResponseRecordDTO(
                        c.getId(),
                        c.getDataHora(),
                        new PacienteResponseRecordDTO(
                                c.getPaciente().getId(),
                                c.getPaciente().getNome(),
                                c.getPaciente().getCpf(),
                                c.getPaciente().getTelefone()
                        ),
                        new MedicoResponseRecordDTO(
                                c.getMedico().getId(),
                                c.getMedico().getNome(),
                                c.getMedico().getCrm()
                        ),
                        c.getDescricao()
                ))
                .toList();
    }
}
