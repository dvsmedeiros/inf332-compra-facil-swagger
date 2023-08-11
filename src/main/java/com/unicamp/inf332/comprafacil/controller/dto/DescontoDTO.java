package com.unicamp.inf332.comprafacil.controller.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DescontoDTO {
    private String id;
    private Double percentual;
    private VigenciaDTO vigencia;
    private StatusDesconto status;
}
