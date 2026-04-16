package com.escola.Senai.ClinicaMedica.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "planos")
public class Plano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 50)
    private String codigo;
    
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(length = 50)
    private String operadora;
    
    @Column(length = 20)
    private String tipo;
    
    @Column(nullable = false)
    private Double desconto;
    
    @Column(nullable = false)
    private Boolean ativo = true;
    
    @Column(length = 500)
    private String observacoes;
    
    @OneToMany(mappedBy = "plano")
    private List<Paciente> pacientes;
}