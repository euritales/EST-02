package com.example.Consultorio.dto;

import com.example.Consultorio.entities.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    public static EnderecoDTO fromEntity(Endereco endereco) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(endereco, EnderecoDTO.class);
    }
    public Endereco toEntity(EnderecoDTO enderecoDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(enderecoDTO, Endereco.class);
    }
}