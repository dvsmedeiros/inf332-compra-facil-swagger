package com.unicamp.inf332.comprafacil.controller.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProdutoDTO extends RepresentationModel<ProdutoDTO> {

    private String id;
    private String sku;
    private String nome;
    private String descricao;
    private List<CategoriaDTO> categoria;
    private BigDecimal preco;
    private List<PrecoDTO> historicoPrecos;
    private List<DescontoDTO> descontos;
    private List<PromocaoDTO> promocoes;
    private List<AlertaDTO> alertas;
    private RankingDTO ranking;
    private LocalDateTime lancamento;

}

