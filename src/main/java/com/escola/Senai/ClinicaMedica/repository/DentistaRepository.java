package com.escola.Senai.ClinicaMedica.repository;

import com.escola.Senai.ClinicaMedica.model.Dentista;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DentistaRepository extends JpaRepository<Dentista, Long> {
    @Query("SELECT d FROM Dentista d WHERE d.nome LIKE %:nome%")
    List<Dentista> findByNomeContaining(@Param("nome") String nome);
    
    @Query("SELECT d FROM Dentista d WHERE d.numeroRegistro = :numeroRegistro")
    List<Dentista> findByNumeroRegistro(@Param("numeroRegistro") String numeroRegistro);
}