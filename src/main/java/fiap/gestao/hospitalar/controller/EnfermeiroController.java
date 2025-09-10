package fiap.gestao.hospitalar.controller;

import fiap.gestao.hospitalar.dto.CreateEnfermeiroRecordDTO;
import fiap.gestao.hospitalar.dto.CreateMedicoRecordDTO;
import fiap.gestao.hospitalar.dto.UpdateEnfermeiroRecordDTO;
import fiap.gestao.hospitalar.dto.UpdateMedicoRecordDTO;
import fiap.gestao.hospitalar.entities.Enfermeiro;
import fiap.gestao.hospitalar.entities.Medico;
import fiap.gestao.hospitalar.service.EnfermeiroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/enfermeiro")
public class EnfermeiroController {

    private final EnfermeiroService enfermeiroService;

    public EnfermeiroController(EnfermeiroService enfermeiroService) {
        this.enfermeiroService = enfermeiroService;
    }

    @PostMapping
    public ResponseEntity<Enfermeiro> save(@RequestBody CreateEnfermeiroRecordDTO input){
        return ResponseEntity.status(HttpStatus.CREATED).body(enfermeiroService.save(input));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody UpdateEnfermeiroRecordDTO input){
        enfermeiroService.update(id, input);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        enfermeiroService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Enfermeiro>> findAll(){
        return ResponseEntity.ok(enfermeiroService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enfermeiro> findById(@PathVariable UUID id){
        return ResponseEntity.ok(enfermeiroService.findById(id));
    }


}
