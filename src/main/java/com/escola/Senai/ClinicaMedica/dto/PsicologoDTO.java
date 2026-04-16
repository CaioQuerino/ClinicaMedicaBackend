package com.escola.Senai.ClinicaMedica.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class PsicologoDTO {
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;
    
    @NotBlank(message = "Número de registro é obrigatório")
    private String numeroRegistro;
    
    @NotBlank(message = "Especialidade é obrigatória")
    private String especialidade;
    
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Telefone invidado")
    private String telefone;
    
    @Email(message = "Email inválido")
    private String email;
}