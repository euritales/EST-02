package com.example.Consultorio.dto;

import com.example.Consultorio.entities.Especialidade;
import com.example.Consultorio.entities.MedicoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicoDTO {
    private String nome;
    private String email;
    private String crm;
    private Especialidade especialidade;

    public static MedicoDTO fromEntity(MedicoEntity medicoEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(medicoEntity, MedicoDTO.class);
    }
    public MedicoEntity toEntity(MedicoDTO medicoDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(medicoDTO, MedicoEntity.class);
    }

}