package com.example.Consultorio.services;

import com.example.Consultorio.dto.ConsultaDTO;
import com.example.Consultorio.entities.Cancelamento;
import com.example.Consultorio.entities.ConsultaEntity;
import com.example.Consultorio.entities.MedicoEntity;
import com.example.Consultorio.entities.PacienteEntity;
import com.example.Consultorio.repository.ConsultaRepository;
import com.example.Consultorio.repository.MedicoRepository;
import com.example.Consultorio.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultaService {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    //#EndPoints-Futuro
    public List<ConsultaEntity> listarConsultas() {
        return consultaRepository.findAll();
    }

    public List<ConsultaDTO> listarConsultasDTO() {
        List<ConsultaEntity> consultas = consultaRepository.findAll();
        List<ConsultaDTO> consultaDTOS = consultas.stream()
                .map(consulta -> ConsultaDTO.fromEntity(consulta)).collect(Collectors.toList());
        return consultaDTOS;
    }

    public ConsultaEntity agendarConsulta(ConsultaEntity consultaEntity) {

        Optional<PacienteEntity> pacienteEntityOptional = pacienteRepository.findByIdAndStatusTrue(consultaEntity.getPaciente().getId());
        if(pacienteEntityOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado ou inativo.");
        }

        PacienteEntity pacienteEntity = pacienteEntityOptional.get();
        if (consultaRepository.existsByPacienteAndDataHoraBetween(
                pacienteEntity, consultaEntity.getDataHora().toLocalDate().atTime(07,00), consultaEntity.getDataHora().toLocalDate().atTime(19,00))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O paciente já possui uma consulta agendada para o mesmo dia");
        }

        Optional<MedicoEntity> medicoEntityOptional = Optional.empty();
        if (consultaEntity.getMedico().getId() != null) {
            medicoEntityOptional = medicoRepository.findByIdAndStatusTrue(consultaEntity.getMedico().getId());
            if (medicoEntityOptional.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado ou inativo.");
            }
        }

        if (medicoEntityOptional.isEmpty()) {
            List<MedicoEntity> medicosList = medicoRepository.findAllByStatusTrue();
            MedicoEntity medicoDisponivel = medicosList.stream()
                    .filter(medico -> !consultaRepository.existsByMedicoAndDataHoraBetween(medico, consultaEntity.getDataHora().minusMinutes(59), consultaEntity.getDataHora().plusMinutes(59)))
                    .findAny()
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sem médicos disponíveis nesse horário."));

            consultaEntity.setMedico(medicoDisponivel);
        } else {
            MedicoEntity medicoEntity = medicoEntityOptional.get();
            if (consultaRepository.existsByMedicoAndDataHoraBetween(medicoEntity, consultaEntity.getDataHora().minusMinutes(59), consultaEntity.getDataHora().plusMinutes(59))){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O médico já possui uma consulta agendada para o mesmo horário");
            }
        }
        if (consultaEntity.getDataHora().toLocalTime().isBefore(LocalTime.of(7, 00)) ||
                consultaEntity.getDataHora().toLocalTime().isAfter(LocalTime.of(18, 00)) ||
                consultaEntity.getDataHora().getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horario para agendamento entre 07:00 e 19:00 de Segunda à Sábado");
        }

        if (consultaEntity.getDataHora().isBefore(LocalDateTime.now().plusMinutes(30))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A consulta deve ser agendada com 30 minutos de antecedência");
        }


        consultaEntity.setPaciente(pacienteEntity);
        consultaEntity.setDataHora(consultaEntity.getDataHora());
        consultaEntity.setStatus(true);

        return consultaRepository.save(consultaEntity);
    }


    public void softDeleteConsulta(Long id, Cancelamento cancelamento) {
        Optional<ConsultaEntity> consultaOptional = consultaRepository.findById(id);

        if(consultaOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada");
        }

        ConsultaEntity consulta = consultaOptional.get();
        if (!consulta.isStatus()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta já está cancelada");
        }
        System.out.println(consulta);
        System.out.println(consultaOptional);
        System.out.println("123123");

        if (LocalDateTime.now().plusHours(24).isAfter( consulta.getDataHora())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A consulta só pode ser cancelada com ao menos 24 horas de antecedência.");
        }
        if(consulta.getCancelamento() == null || !EnumSet.allOf(Cancelamento.class).contains(cancelamento)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Motivo de cancelamento inválido.Informe o motivo do cancelamento: PACIENTE_DESISTIU, MEDICO_CANCELOU, OUTROS");
        }

        consulta.setStatus(false);
        consulta.setCancelamento(cancelamento);
        consultaRepository.save(consulta);
        new ResponseStatusException(HttpStatus.OK);
        return;
    }
}