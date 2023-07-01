package com.example.Consultorio.services;

import com.example.Consultorio.dto.MedicoDTO;
import com.example.Consultorio.dto.PacienteDTO;
import com.example.Consultorio.entities.MedicoEntity;
import com.example.Consultorio.entities.PacienteEntity;
import com.example.Consultorio.repository.MedicoRepository;
import com.example.Consultorio.repository.PacienteRepository;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    //#EndPoints-Futuro
    public List<PacienteEntity> listarPacientes() {
        List<PacienteEntity> pacientes = pacienteRepository.findAll();
        return pacientes;
    }
    //#EndPoints-Futuro
    public Optional<PacienteEntity> listarPacientesPorId(Long id) {
        Optional<PacienteEntity> paciente = pacienteRepository.findByIdAndStatusTrue(id);
        if(paciente.isPresent()){
            return Optional.of(paciente.get());
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado");
        }
    }
    public Page<PacienteDTO> listarPacientesAtivos(int pagina) {
        Pageable pageable = PageRequest.of(pagina, 10, Sort.by("nome").ascending());
        Page<PacienteEntity> medicoPage = pacienteRepository.findAllByStatusTrueOrderByNomeAsc(pageable);
        return medicoPage.map(PacienteDTO::fromEntity);
    }

    public PacienteDTO cadastrarPaciente(PacienteEntity pacienteEntity) {
        Assert.isNull(pacienteEntity.getId(), "Não foi possivel salvar este registro");
        pacienteEntity.setStatus(true); // Definir status como ativo por padrão
        System.out.println(pacienteEntity);
        PacienteEntity paciente = pacienteRepository.save(pacienteEntity);
        return PacienteDTO.fromEntity(paciente);
    }

    public PacienteDTO atualizarPaciente(Long id, PacienteEntity pacienteEntity) {
        Assert.isNull(pacienteEntity.getId(), "Não foi possivel salvar este registro");
        PacienteEntity paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado"));

        if(!paciente.isStatus()){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado");
        }

        if (!pacienteEntity.getEmail().equals(paciente.getEmail()) ||
                !pacienteEntity.getCpf().equals(paciente.getCpf()) ||
                !pacienteEntity.isStatus())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campos inválidos para atualização");
        }

        paciente.setNome(pacienteEntity.getNome());
        paciente.setTelefone(pacienteEntity.getTelefone());
        paciente.setEndereco(pacienteEntity.getEndereco());
        pacienteRepository.save(paciente);
        return null;
    }

    public void softDeletePaciente(Long id) {
        PacienteEntity paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado"));
        if (!paciente.isStatus()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paciente inativo");
        }
        paciente.setStatus(false);
        pacienteRepository.save(paciente);
        new ResponseStatusException(HttpStatus.OK);
        return;
    }
}