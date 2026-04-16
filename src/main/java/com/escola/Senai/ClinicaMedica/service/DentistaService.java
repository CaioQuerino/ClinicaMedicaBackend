package com.escola.Senai.ClinicaMedica.service;

import com.escola.Senai.ClinicaMedica.dto.DentistaDTO;
import com.escola.Senai.ClinicaMedica.model.Dentista;
import com.escola.Senai.ClinicaMedica.enums.TypeConselho;
import com.escola.Senai.ClinicaMedica.repository.DentistaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DentistaService {
    
    @Autowired
    private DentistaRepository dentistaRepository;
    
    public List<DentistaDTO> listarTodos() {
        return dentistaRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    public DentistaDTO buscarPorId(Long id) {
        Dentista dentista = dentistaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dentista não encontrado com ID: " + id));
        return converterParaDTO(dentista);
    }
    
    public List<DentistaDTO> buscarPorNome(String nome) {
        return dentistaRepository.findByNomeContaining(nome).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    public DentistaDTO inserir(DentistaDTO dentistaDTO) {
        Dentista dentista = converterParaEntidade(dentistaDTO);
        dentista = dentistaRepository.save(dentista);
        return converterParaDTO(dentista);
    }
    
    public DentistaDTO atualizar(Long id, DentistaDTO dentistaDTO) {
        Dentista dentista = dentistaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dentista não encontrado com ID: " + id));
        
        dentista.setNome(dentistaDTO.getNome());
        dentista.setNumeroRegistro(dentistaDTO.getNumeroRegistro());
        dentista.setEspecialidade(dentistaDTO.getEspecialidade());
        dentista.setTelefone(dentistaDTO.getTelefone());
        dentista.setEmail(dentistaDTO.getEmail());
        
        dentista = dentistaRepository.save(dentista);
        return converterParaDTO(dentista);
    }
    
    public void deletar(Long id) {
        if (!dentistaRepository.existsById(id)) {
            throw new EntityNotFoundException("Dentista não encontrado com ID: " + id);
        }
        dentistaRepository.deleteById(id);
    }
    
    private DentistaDTO converterParaDTO(Dentista dentista) {
        DentistaDTO dto = new DentistaDTO();
        dto.setId(dentista.getId());
        dto.setNome(dentista.getNome());
        dto.setNumeroRegistro(dentista.getNumeroRegistro());
        dto.setEspecialidade(dentista.getEspecialidade());
        dto.setTelefone(dentista.getTelefone());
        dto.setEmail(dentista.getEmail());
        return dto;
    }
    
    private Dentista converterParaEntidade(DentistaDTO dto) {
        Dentista dentista = new Dentista();
        dentista.setNome(dto.getNome());
        dentista.setNumeroRegistro(dto.getNumeroRegistro());
        dentista.setConselho(TypeConselho.CRO);
        dentista.setEspecialidade(dto.getEspecialidade());
        dentista.setTelefone(dto.getTelefone());
        dentista.setEmail(dto.getEmail());
        return dentista;
    }
}