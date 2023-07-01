package com.example.Consultorio.services;

import com.example.Consultorio.dto.MedicoDTO;
import com.example.Consultorio.entities.MedicoEntity;
import com.example.Consultorio.repository.MedicoRepository;
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
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;

    //#EndPoints-Futuro
    public List<MedicoEntity> listarMedicos() {
        List<MedicoEntity> medicos = medicoRepository.findAll();
        return medicos;

    }
    //#EndPoints-Futuro
    public Optional<MedicoEntity> listarMedicosPorId(Long id) {
        Optional<MedicoEntity> medicoEntity = medicoRepository.findByIdAndStatusTrue(id);
        if(medicoEntity.isPresent()){
            return Optional.of(medicoEntity.get());
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medico não encontrado");
        }
    }
    public Page<MedicoDTO> listarMedicosAtivos(int pagina) {
        Pageable pageable = PageRequest.of(pagina, 10, Sort.by("nome").ascending());
        Page<MedicoEntity> medicoPage = medicoRepository.findAllByStatusTrueOrderByNomeAsc(pageable);
        return medicoPage.map(MedicoDTO::fromEntity);
    }

    public MedicoDTO cadastrarMedico(MedicoEntity medicoEntity) {
        Assert.isNull(medicoEntity.getId(), "Não foi possivel salvar este registro");
        medicoEntity.setStatus(true); // Definir status como ativo por padrão
        System.out.println(medicoEntity);
        MedicoEntity medico = medicoRepository.save(medicoEntity);
        return MedicoDTO.fromEntity(medico);
    }
    public MedicoDTO atualizarMedico(Long id, MedicoEntity medicoEntity) {
        Assert.isNull(medicoEntity.getId(), "Não foi possivel salvar este registro");
        MedicoEntity medico = medicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado"));

        if(!medico.isStatus()){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Medico não encontrado");
        }

        if (!medicoEntity.getEmail().equals(medico.getEmail()) ||
                !medicoEntity.getCrm().equals(medico.getCrm()) ||
                !medicoEntity.isStatus()||
                !medicoEntity.getEspecialidade().equals(medico.getEspecialidade())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campos inválidos para atualização");
        }

        medico.setNome(medicoEntity.getNome());
        medico.setTelefone(medicoEntity.getTelefone());
        medico.setEndereco(medicoEntity.getEndereco());
        medicoRepository.save(medico);
        return null;
    }

    public void softDeleteMedico(Long id) {
        MedicoEntity medico = medicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medico não encontrado"));
        if (!medico.isStatus()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Medico inativo");
        }
        medico.setStatus(false);
        medicoRepository.save(medico);
        new ResponseStatusException(HttpStatus.OK);
        return;
    }

}