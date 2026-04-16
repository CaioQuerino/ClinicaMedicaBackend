package com.escola.Senai.ClinicaMedica.model;

import com.escola.Senai.ClinicaMedica.enums.TypeConselho;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "dentistas")
public class Dentista extends Profissional {
    @Column(nullable = false, unique = true)
    private String numeroRegistro;

    public Dentista(String numeroRegistro, Long id, String nome, TypeConselho conselho, String especialidade, String telefone, String email, List<Consulta> consultas) {
        super(id, nome, TypeConselho.CRO, especialidade, telefone, email, consultas);
        this.numeroRegistro = numeroRegistro;
    }
}