package com.unicamp.inf332.comprafacil.controller.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PrecoDTO {
    private BigDecimal valor;
    private String moeda;
    private VigenciaDTO vigencia;
}
