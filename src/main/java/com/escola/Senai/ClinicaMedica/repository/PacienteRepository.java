package com.escola.Senai.ClinicaMedica.repository;

import com.escola.Senai.ClinicaMedica.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    @Query("SELECT p FROM Paciente p WHERE p.nome LIKE %:nome%")
    List<Paciente> findByNomeContaining(@Param("nome") String nome);
    
    @Query("SELECT p FROM Paciente p WHERE p.cpf = :cpf")
    Optional<Paciente> findByCpf(@Param("cpf") String cpf);
}