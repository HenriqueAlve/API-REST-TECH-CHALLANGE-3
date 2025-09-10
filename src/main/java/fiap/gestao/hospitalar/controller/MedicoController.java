package fiap.gestao.hospitalar.controller;

import fiap.gestao.hospitalar.dto.CreateMedicoRecordDTO;
import fiap.gestao.hospitalar.dto.UpdateMedicoRecordDTO;
import fiap.gestao.hospitalar.entities.Medico;
import fiap.gestao.hospitalar.service.MedicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/medico")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService){
        this.medicoService = medicoService;

    }

    @PostMapping
    public ResponseEntity<Medico> save(@RequestBody CreateMedicoRecordDTO input){
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoService.save(input));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody UpdateMedicoRecordDTO input){
    medicoService.update(id, input);
    return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        medicoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Medico>> findAll(){
        return ResponseEntity.ok(medicoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> findById(@PathVariable UUID id){
        return ResponseEntity.ok(medicoService.findById(id));
    }
}
