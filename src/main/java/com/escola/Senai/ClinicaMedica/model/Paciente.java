package com.escola.Senai.ClinicaMedica.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter 
@Setter
@Entity
@Table(name = "pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;
    
    @Column(nullable = false)
    private LocalDate dataNascimento;
    
    @Column(length = 20)
    private String telefone;
    
    @Column(length = 200)
    private String endereco;
    
    @ManyToOne
    @JoinColumn(name = "plano_id")
    private Plano plano;
    
    @OneToMany(mappedBy = "paciente")
    private List<Consulta> consultas;
}