package fiap.gestao.hospitalar.controller;

import fiap.gestao.hospitalar.dto.ConsultaResponseRecordDTO;
import fiap.gestao.hospitalar.dto.CreateConsultaRecordDTO;
import fiap.gestao.hospitalar.dto.UpdateConsultaRecordDTO;
import fiap.gestao.hospitalar.entities.Consulta;
import fiap.gestao.hospitalar.service.ConsultaPublisherService;
import fiap.gestao.hospitalar.service.ConsultaService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;
    private final ConsultaPublisherService consultaPublisherService;

    public ConsultaController(ConsultaService consultaService, ConsultaPublisherService consultaPublisherService) {
        this.consultaService = consultaService;
        this.consultaPublisherService = consultaPublisherService;
    }


    @PostMapping
    public ResponseEntity<Consulta> save(@Valid @RequestBody CreateConsultaRecordDTO input){
        return ResponseEntity.status(HttpStatus.CREATED).body(consultaService.save(input));
    }

    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<ConsultaResponseRecordDTO>> listarPorMedico(@PathVariable UUID medicoId) {
        var consultas = consultaService.listarPorMedico(medicoId);
        return ResponseEntity.ok(consultas);
    }

    @GetMapping
    public ResponseEntity<List<ConsultaResponseRecordDTO>> findAllConsultas() {
        return ResponseEntity.ok(consultaService.findAllConsultas());
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ConsultaResponseRecordDTO>> listarConsultaPorPaciente(@PathVariable UUID pacienteId) {
       var consultaPaciente= consultaService.listarConsultaPorPaciente(pacienteId);
       return ResponseEntity.ok(consultaPaciente);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @PathVariable UUID id, @RequestBody UpdateConsultaRecordDTO input){
        consultaService.update(id,input);
        return ResponseEntity.noContent().build();
    }
}
