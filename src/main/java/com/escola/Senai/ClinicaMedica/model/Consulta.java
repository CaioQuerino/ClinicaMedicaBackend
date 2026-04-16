package com.escola.Senai.ClinicaMedica.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "consultas")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "profissional_id", nullable = false)
    private Profissional profissional;
    
    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
    
    @Column(nullable = false)
    private LocalDateTime dataHora;
    
    @Column(length = 500)
    private String observacao;
    
    @Column(length = 50)
    private String status;
}