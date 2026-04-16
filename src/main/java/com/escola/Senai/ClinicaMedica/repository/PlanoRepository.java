package com.escola.Senai.ClinicaMedica.repository;

import com.escola.Senai.ClinicaMedica.model.Plano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface PlanoRepository extends JpaRepository<Plano, Long> {
    @Query("SELECT p FROM Plano p WHERE p.nome LIKE %:nome%")
    List<Plano> findByNomeContaining(@Param("nome") String nome);
    
    @Query("SELECT p FROM Plano p WHERE p.codigo = :codigo")
    Optional<Plano> findByCodigo(@Param("codigo") String codigo);
    
    @Query("SELECT p FROM Plano p WHERE p.ativo = true")
    List<Plano> findAtivos();
    
    @Query("SELECT p FROM Plano p WHERE p.operadora = :operadora")
    List<Plano> findByOperadora(@Param("operadora") String operadora);
}