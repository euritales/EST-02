package com.example.Consultorio.repository;

import com.example.Consultorio.entities.MedicoEntity;
import com.example.Consultorio.entities.PacienteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<PacienteEntity, Long> {
    Page<PacienteEntity> findAllByStatusTrueOrderByNomeAsc(Pageable pageable);
    Optional<PacienteEntity> findByIdAndStatusTrue(Long id);
}