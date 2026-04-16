package com.escola.Senai.ClinicaMedica.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class PlanoDTO {
    private Long id;
    
    @NotBlank(message = "Código do plano é obrigatório")
    @Size(min = 3, max = 50, message = "Código deve ter entre 3 e 50 caracteres")
    private String codigo;
    
    @NotBlank(message = "Nome do plano é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;
    
    @Size(max = 50, message = "Operadora deve ter no máximo 50 caracteres")
    private String operadora;
    
    @Size(max = 50, message = "Tipo deve ter no máximo 50 caracteres")
    private String tipo;
    
    @NotNull(message = "Desconto é obrigatório")
    @DecimalMin(value = "0.0", message = "Desconto não pode ser negativo")
    @DecimalMax(value = "100.0", message = "Desconto não pode ser maior que 100%")
    private Double desconto;
    
    private Boolean ativo;
    
    private String observacoes;
}