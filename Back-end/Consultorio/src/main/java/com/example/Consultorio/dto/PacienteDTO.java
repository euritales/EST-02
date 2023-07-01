package com.example.Consultorio.dto;

import com.example.Consultorio.entities.PacienteEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDTO {
    private String nome;
    private String email;
    private String cpf;


    public static PacienteDTO fromEntity(PacienteEntity pacienteEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(pacienteEntity, PacienteDTO.class);
    }

    public PacienteEntity toEntity(PacienteDTO pacienteDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(pacienteDTO, PacienteEntity.class);
    }
}