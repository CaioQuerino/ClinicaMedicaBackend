package com.escola.Senai.ClinicaMedica.enums;

public enum TypeConselho {
    CRM("Conselho Regional de Medicina"),
    CREFITO("Conselho Regional de Fisioterapia e Terapia Ocupacional"),
    CRO("Conselho Regional de Odontologia"),
    CRP("Conselho Regional de Psicologia");
    
    private final String descricao;
    
    TypeConselho(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}