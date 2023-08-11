package com.unicamp.inf332.comprafacil.controller.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AlertaDTO extends RepresentationModel<AlertaDTO> {
    private String id;
    private String ProdutoId;
    private String callback;
}
