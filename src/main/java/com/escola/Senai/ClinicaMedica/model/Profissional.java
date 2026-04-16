package com.escola.Senai.ClinicaMedica.model;

import com.escola.Senai.ClinicaMedica.enums.TypeConselho;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profissional")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Profissional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeConselho conselho;
    
    @Column(nullable = false, length = 50)
    private String especialidade;
    
    @Column(length = 20)
    private String telefone;
    
    @Column(length = 100)
    private String email;
    
    @OneToMany(mappedBy = "profissional")
    private List<Consulta> consultas;
}