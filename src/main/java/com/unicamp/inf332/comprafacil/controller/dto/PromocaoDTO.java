package com.unicamp.inf332.comprafacil.controller.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PromocaoDTO extends RepresentationModel<PromocaoDTO> {
    private String id;
    private String condicao;
    private List<DescontoDTO> descontos;
    private VigenciaDTO vigencia;
    private StatusPromocao status;
}
