package com.unicamp.inf332.comprafacil.controller.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RankingDTO extends RepresentationModel<RankingDTO> {
    private String posicao;
}
