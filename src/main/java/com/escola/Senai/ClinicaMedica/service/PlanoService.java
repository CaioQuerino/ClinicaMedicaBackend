package com.escola.Senai.ClinicaMedica.service;

import com.escola.Senai.ClinicaMedica.dto.PlanoDTO;
import com.escola.Senai.ClinicaMedica.model.Plano;
import com.escola.Senai.ClinicaMedica.repository.PlanoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanoService {
    
    @Autowired
    private PlanoRepository planoRepository;
    
    public List<PlanoDTO> listarTodos() {
        return planoRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    public List<PlanoDTO> listarAtivos() {
        return planoRepository.findAtivos().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    public PlanoDTO buscarPorId(Long id) {
        Plano plano = planoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plano não encontrado com ID: " + id));
        return converterParaDTO(plano);
    }
    
    public List<PlanoDTO> buscarPorNome(String nome) {
        return planoRepository.findByNomeContaining(nome).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    public PlanoDTO inserir(PlanoDTO planoDTO) {
        Plano plano = converterParaEntidade(planoDTO);
        plano = planoRepository.save(plano);
        return converterParaDTO(plano);
    }
    
    public PlanoDTO atualizar(Long id, PlanoDTO planoDTO) {
        Plano plano = planoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plano não encontrado com ID: " + id));
        
        plano.setCodigo(planoDTO.getCodigo());
        plano.setNome(planoDTO.getNome());
        plano.setOperadora(planoDTO.getOperadora());
        plano.setTipo(planoDTO.getTipo());
        plano.setDesconto(planoDTO.getDesconto());
        plano.setAtivo(planoDTO.getAtivo() != null ? planoDTO.getAtivo() : plano.getAtivo());
        plano.setObservacoes(planoDTO.getObservacoes());
        
        plano = planoRepository.save(plano);
        return converterParaDTO(plano);
    }
    
    public void deletar(Long id) {
        if (!planoRepository.existsById(id)) {
            throw new EntityNotFoundException("Plano não encontrado com ID: " + id);
        }
        planoRepository.deleteById(id);
    }
    
    private PlanoDTO converterParaDTO(Plano plano) {
        PlanoDTO dto = new PlanoDTO();
        dto.setId(plano.getId());
        dto.setCodigo(plano.getCodigo());
        dto.setNome(plano.getNome());
        dto.setOperadora(plano.getOperadora());
        dto.setTipo(plano.getTipo());
        dto.setDesconto(plano.getDesconto());
        dto.setAtivo(plano.getAtivo());
        dto.setObservacoes(plano.getObservacoes());
        return dto;
    }
    
    private Plano converterParaEntidade(PlanoDTO dto) {
        Plano plano = new Plano();
        plano.setCodigo(dto.getCodigo());
        plano.setNome(dto.getNome());
        plano.setOperadora(dto.getOperadora());
        plano.setTipo(dto.getTipo());
        plano.setDesconto(dto.getDesconto());
        plano.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);
        plano.setObservacoes(dto.getObservacoes());
        return plano;
    }
}