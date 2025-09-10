package fiap.gestao.hospitalar.service;

import fiap.gestao.hospitalar.dto.*;
import fiap.gestao.hospitalar.entities.Consulta;
import fiap.gestao.hospitalar.repository.ConsultaRepository;
import fiap.gestao.hospitalar.repository.MedicoRepository;
import fiap.gestao.hospitalar.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ConsultaService {

    private final  ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public ConsultaService(ConsultaRepository consultaRepository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }


    public Consulta save(CreateConsultaRecordDTO input) {
        var medico = medicoRepository.findById(input.medicoId()).orElseThrow(
                ()-> new RuntimeException("Medico nao encontrado")
        );
        var paciente = pacienteRepository.findById(input.pacienteId()).orElseThrow(
                ()-> new RuntimeException("Paciente nao encontrado")
        );
        Consulta consulta = new Consulta();
        consulta.setMedico(medico);
        consulta.setPaciente(paciente);
        consulta.setDataHora(input.dataHora());
        consulta.setDescricao(input.descricao());
        return consultaRepository.save(consulta);
    }

    public List<ConsultaResponseRecordDTO> listarPorMedico(UUID medicoId) {
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

    public void update(UUID id, UpdateConsultaRecordDTO input) {
        var consulta = consultaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Consulta não encontrada")
        );
        consulta.setDataHora(input.dataHora());
        consulta.setDescricao(input.descricao());
        consultaRepository.save(consulta);
    }


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


    public  List<ConsultaResponseRecordDTO> listarConsultaPorPaciente(UUID pacienteId) {
        var consultaPaciente = consultaRepository.findAllByPacienteId(pacienteId);
        return consultaPaciente.stream()
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
