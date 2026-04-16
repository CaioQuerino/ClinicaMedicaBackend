package com.escola.Senai.ClinicaMedica.repository;

import com.escola.Senai.ClinicaMedica.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    @Query("SELECT m FROM Medico m WHERE m.nome LIKE %:nome%")
    List<Medico> findByNomeContaining(@Param("nome") String nome);
    
    @Query("SELECT m FROM Medico m WHERE m.especialidade = :especialidade")
    List<Medico> findByEspecialidade(@Param("especialidade") String especialidade);
    
    @Query("SELECT m FROM Medico m WHERE m.numeroRegistro = :numeroRegistro")
    List<Medico> findByNumeroRegistro(@Param("numeroRegistro") String numeroRegistro);
}