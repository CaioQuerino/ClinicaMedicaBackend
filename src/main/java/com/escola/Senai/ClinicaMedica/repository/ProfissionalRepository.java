package com.escola.Senai.ClinicaMedica.repository;

import com.escola.Senai.ClinicaMedica.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
    @Query("SELECT p FROM Profissional p WHERE p.nome LIKE %:nome%")
    List<Profissional> findByNomeContaining(@Param("nome") String nome);
    
    @Query("SELECT p FROM Profissional p WHERE p.especialidade = :especialidade")
    List<Profissional> findByEspecialidade(@Param("especialidade") String especialidade);
}