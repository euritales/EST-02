package com.example.Consultorio.controllers;

import com.example.Consultorio.dto.MedicoDTO;
import com.example.Consultorio.entities.MedicoEntity;
import com.example.Consultorio.services.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    //#EndPoints-Futuro
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<MedicoEntity>> listarMedicos() {
        return ResponseEntity.ok(medicoService.listarMedicos());
    }

    //#EndPoints-Futuro
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Optional<MedicoEntity>> buscarPaciente(@PathVariable Long id) {
        return ResponseEntity.ok(medicoService.listarMedicosPorId(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<MedicoDTO>> listarMedicosAtivos(@RequestParam(defaultValue = "0") int pagina) {
        Page<MedicoDTO> medicoDTO = medicoService.listarMedicosAtivos(pagina);
        return ResponseEntity.ok(medicoDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity cadastrarMedico(@RequestBody MedicoEntity medicoEntity) {
        MedicoDTO novoMedico = medicoService.cadastrarMedico(medicoEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMedico);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity atualizarMedico(@PathVariable Long id, @RequestBody MedicoEntity medicoEntity) {
        return ResponseEntity.ok(medicoService.atualizarMedico(id, medicoEntity));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> excluirMedico(@PathVariable Long id){
        medicoService.softDeleteMedico(id);
        return ResponseEntity.noContent().build();
    }
}