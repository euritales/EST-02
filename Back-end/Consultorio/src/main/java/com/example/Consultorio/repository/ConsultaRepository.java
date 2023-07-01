package com.example.Consultorio.repository;

import com.example.Consultorio.entities.ConsultaEntity;
import com.example.Consultorio.entities.MedicoEntity;
import com.example.Consultorio.entities.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConsultaRepository extends JpaRepository<ConsultaEntity, Long> {
    boolean existsByMedicoAndDataHora(MedicoEntity medico, LocalDateTime dataHora);
    boolean existsByPacienteAndDataHoraBetween(PacienteEntity paciente, LocalDateTime startDateTime, LocalDateTime endDateTime);
    boolean existsByMedicoAndDataHoraBetween(MedicoEntity medico, LocalDateTime startDateTime, LocalDateTime endDateTime);

}