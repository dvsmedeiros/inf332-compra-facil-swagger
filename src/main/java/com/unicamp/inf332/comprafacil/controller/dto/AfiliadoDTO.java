package com.unicamp.inf332.comprafacil.controller.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AfiliadoDTO extends RepresentationModel<AfiliadoDTO> {

    private String id;
    private String nome;
    private String email;
    private EnderecoDTO endereco;
    private String cnpj;
}
