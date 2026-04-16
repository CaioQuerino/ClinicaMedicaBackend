package com.escola.Senai.ClinicaMedica.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class ConsultaDTO {
    private Long id;
    
    @NotNull(message = "ID do profissional é obrigatório")
    private Long profissionalId;

    private String nomeProfissional;
    
    @NotNull(message = "ID do paciente é obrigatório")
    private Long pacienteId;

    private String nomePaciente;
    
    @NotNull(message = "Data e hora são obrigatórias")
    @Future(message = "Data e hora devem ser futuras")
    private LocalDateTime dataHora;
    
    private String observacao;
    
    private String status;
}