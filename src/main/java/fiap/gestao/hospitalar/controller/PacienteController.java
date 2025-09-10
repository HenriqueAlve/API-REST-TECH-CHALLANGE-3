package fiap.gestao.hospitalar.controller;

import fiap.gestao.hospitalar.dto.CreateMedicoRecordDTO;
import fiap.gestao.hospitalar.dto.CreatePacienteRecordDTO;
import fiap.gestao.hospitalar.dto.UpdateMedicoRecordDTO;
import fiap.gestao.hospitalar.dto.UpdatePacienteRecordDTo;
import fiap.gestao.hospitalar.entities.Medico;
import fiap.gestao.hospitalar.entities.Paciente;
import fiap.gestao.hospitalar.service.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<Paciente> save(@RequestBody CreatePacienteRecordDTO input){
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteService.save(input));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody UpdatePacienteRecordDTo input){
        pacienteService.update(id, input);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        pacienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> findAll(){
        return ResponseEntity.ok(pacienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> findById(@PathVariable UUID id){
        return ResponseEntity.ok(pacienteService.findById(id));
    }


}
