package com.escola.Senai.ClinicaMedica.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
public class PacienteDTO {
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;
    
    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "^[0-9]{11}$", message = "CPF deve conter 11 dígitos")
    private String cpf;
    
    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate dataNascimento;
    
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Telefone inválido")
    private String telefone;
    
    private String endereco;
}