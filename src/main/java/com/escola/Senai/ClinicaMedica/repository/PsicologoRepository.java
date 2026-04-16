package com.escola.Senai.ClinicaMedica.repository;

import com.escola.Senai.ClinicaMedica.model.Psicologo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PsicologoRepository extends JpaRepository<Psicologo, Long> {
    @Query("SELECT p FROM Psicologo p WHERE p.nome LIKE %:nome%")
    List<Psicologo> findByNomeContaining(@Param("nome") String nome);
    
    @Query("SELECT p FROM Psicologo p WHERE p.numeroRegistro = :numeroRegistro")
    List<Psicologo> findByNumeroRegistro(@Param("numeroRegistro") String numeroRegistro);
}