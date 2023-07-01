package com.example.Consultorio.dto;

import com.example.Consultorio.entities.Cancelamento;
import com.example.Consultorio.entities.ConsultaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaDTO {
    private PacienteDTO paciente;
    private MedicoDTO medico;
    private LocalDateTime dataHora;
    private Cancelamento cancelamento;

    public static ConsultaDTO fromEntity(ConsultaEntity consultaEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(consultaEntity, ConsultaDTO.class);
    }
    public ConsultaEntity toEntity(ConsultaDTO consultaDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(consultaDTO, ConsultaEntity.class);
    }
}