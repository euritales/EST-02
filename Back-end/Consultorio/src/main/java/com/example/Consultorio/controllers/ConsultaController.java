package com.example.Consultorio.controllers;

import com.example.Consultorio.dto.ConsultaDTO;
import com.example.Consultorio.entities.ConsultaEntity;
import com.example.Consultorio.services.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    //#EndPoints-Futuro
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ConsultaEntity>> listarConsultas() {
        return ResponseEntity.ok(consultaService.listarConsultas());
    }
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ConsultaDTO>> listarConsultasDTO() {
        return ResponseEntity.ok(consultaService.listarConsultasDTO());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity agendarConsulta(@RequestBody ConsultaEntity consultaEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(consultaService.agendarConsulta(consultaEntity));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> excluirConsulta(@PathVariable Long id,  @RequestBody ConsultaEntity consulta){
        consultaService.softDeleteConsulta(id, consulta.getCancelamento());
        return ResponseEntity.noContent().build();
    }

}