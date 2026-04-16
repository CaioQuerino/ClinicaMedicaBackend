package com.escola.Senai.ClinicaMedica.repository;

import com.escola.Senai.ClinicaMedica.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    @Query("SELECT c FROM Consulta c WHERE c.profissional.id = :profissionalId")
    List<Consulta> findByProfissionalId(@Param("profissionalId") Long profissionalId);
    
    @Query("SELECT c FROM Consulta c WHERE c.paciente.id = :pacienteId")
    List<Consulta> findByPacienteId(@Param("pacienteId") Long pacienteId);
    
    @Query("SELECT c FROM Consulta c WHERE c.dataHora BETWEEN :inicio AND :fim")
    List<Consulta> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}