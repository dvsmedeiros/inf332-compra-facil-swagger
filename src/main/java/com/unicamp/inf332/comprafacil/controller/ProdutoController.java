package com.unicamp.inf332.comprafacil.controller;

import com.unicamp.inf332.comprafacil.controller.dto.AlertaDTO;
import com.unicamp.inf332.comprafacil.controller.dto.ProdutoDTO;
import com.unicamp.inf332.comprafacil.controller.dto.PromocaoDTO;
import com.unicamp.inf332.comprafacil.controller.dto.RankingDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Produto", description = "Produto Resource")
@RestController
public class ProdutoController {

    @Operation(summary = "Obter uma lista de produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos encontrados",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Parâmetros fornecidos são inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Produtos não encontrados", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content),
    })
    @GetMapping("/v1/{afiliadoId}/produto")
    public ResponseEntity<Page<ProdutoDTO>> get(
            @Parameter(description = "ID do afiliado") @PathVariable String afiliadoId,
            @Parameter(description = "Nome da categoria") @RequestParam(required = false) String categoria,
            @Parameter(description = "Nome ou descrição do produto") @RequestParam(required = false) String descricao,
            @Parameter(description = "Produtos com desconto") @RequestParam(required = false) Boolean desconto,
            @Parameter(description = "Faixa incial de preço, De: ") @RequestParam(required = false) Double precoDe,
            @Parameter(description = "Faixa final de preço, Até: ") @RequestParam(required = false) Double precoAte,
            @ParameterObject Pageable page) {

        ProdutoDTO produto = ProdutoDTO.builder().build();
        List<ProdutoDTO> produtos = Arrays.asList(produto);
        for (ProdutoDTO p : produtos) {
            p.add(linkTo(methodOn(ProdutoController.class).getId(afiliadoId, p.getId())).withSelfRel());
            p.add(linkTo(methodOn(ProdutoController.class).getRanking(afiliadoId, p.getId())).withSelfRel());
            p.add(linkTo(methodOn(ProdutoController.class).getAlertas(afiliadoId, p.getId(), page)).withSelfRel());
            p.add(linkTo(methodOn(ProdutoController.class).getPromocoes(afiliadoId, p.getId(), page)).withSelfRel());
            p.add(linkTo(methodOn(ProdutoController.class).alerta(afiliadoId, p.getId(), "http://localhost:8080/callback")).withSelfRel());
        }
        CollectionModel<ProdutoDTO> collectionModel = CollectionModel.of(produtos);
        collectionModel.add(linkTo(methodOn(ProdutoController.class).get(afiliadoId, categoria, descricao, desconto, precoDe, precoAte, page)).withSelfRel());

        return ResponseEntity.ok(new PageImpl<>(collectionModel.getContent().stream().toList()));
    }

    @Operation(summary = "Obter o catálogo de produtos associados ao afiliado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Catálogo de produtos encontrados",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Parâmetros fornecidos são inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Produtos não encontrados", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content),
    })
    @GetMapping("/v1/{afiliadoId}/produto/catalogo")
    public ResponseEntity<Page<ProdutoDTO>> getCatalogo(
            @Parameter(description = "ID do afiliado") @PathVariable String afiliadoId,
            @Parameter(description = "Nome da categoria") @RequestParam(required = false) String categoria,
            @Parameter(description = "Nome ou descrição do produto") @RequestParam(required = false) String descricao,
            @Parameter(description = "Produtos com desconto") @RequestParam(required = false) Boolean desconto,
            @Parameter(description = "Faixa incial de preço, De: ") @RequestParam(required = false) Double precoDe,
            @Parameter(description = "Faixa final de preço, Até: ") @RequestParam(required = false) Double precoAte,
            @ParameterObject Pageable page) {

        ProdutoDTO produto = ProdutoDTO.builder().build();
        List<ProdutoDTO> produtos = Arrays.asList(produto);
        for (ProdutoDTO p : produtos) {
            p.add(linkTo(methodOn(ProdutoController.class).getId(afiliadoId, p.getId())).withSelfRel());
            p.add(linkTo(methodOn(ProdutoController.class).getRanking(afiliadoId, p.getId())).withSelfRel());
            p.add(linkTo(methodOn(ProdutoController.class).getAlertas(afiliadoId, p.getId(), page)).withSelfRel());
            p.add(linkTo(methodOn(ProdutoController.class).getPromocoes(afiliadoId, p.getId(), page)).withSelfRel());
            p.add(linkTo(methodOn(ProdutoController.class).alerta(afiliadoId, p.getId(), "http://localhost:8080/callback")).withSelfRel());
        }
        CollectionModel<ProdutoDTO> collectionModel = CollectionModel.of(produtos);
        collectionModel.add(linkTo(methodOn(ProdutoController.class).getCatalogo(afiliadoId, categoria, descricao, desconto, precoDe, precoAte, page)).withSelfRel());

        return ResponseEntity.ok(new PageImpl<>(collectionModel.getContent().stream().toList()));
    }

    @Operation(summary = "Obter promções de produtos por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ranking de produtos encontrados",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PromocaoDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Parâmetro fornecido inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content),
    })
    @GetMapping("/v1/{afiliadoId}/produto/{id}/promocoes")
    public ResponseEntity<Page<PromocaoDTO>> getPromocoes(
            @Parameter(description = "ID do afiliado") @PathVariable String afiliadoId,
            @Parameter(description = "ID do produto") @PathVariable String id,
            @ParameterObject Pageable page) {

        ProdutoDTO produto = ProdutoDTO.builder()
                .promocoes(Arrays.asList(PromocaoDTO.builder().build()))
                .build();
        List<PromocaoDTO> promocoes = Arrays.asList(produto)
                .stream()
                .flatMap(p -> p.getPromocoes().stream())
                .map(p -> {
                    p.add(linkTo(methodOn(ProdutoController.class).getId(afiliadoId, p.getId())).withSelfRel());
                    p.add(linkTo(methodOn(ProdutoController.class).alerta(afiliadoId, p.getId(), "http://localhost:8080/callback")).withSelfRel());
                    p.add(linkTo(methodOn(ProdutoController.class).getRanking(afiliadoId, p.getId())).withSelfRel());
                    p.add(linkTo(methodOn(ProdutoController.class).getAlertas(afiliadoId, p.getId(), page)).withSelfRel());
                    return p;
                }).toList();

        CollectionModel<PromocaoDTO> collectionModel = CollectionModel.of(promocoes);
        collectionModel.add(linkTo(methodOn(ProdutoController.class).getPromocoes(afiliadoId, id, page)).withSelfRel());

        return ResponseEntity.ok(new PageImpl<>(collectionModel.getContent().stream().toList()));
    }

    @Operation(summary = "Obter promoções de produtos por id de categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Promoçoes de produtos encontrados",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PromocaoDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Parâmetro fornecido inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content),
    })
    @GetMapping("/v1/{afiliadoId}/produto/categoria/{id}/promocoes")
    public ResponseEntity<Page<PromocaoDTO>> getPromocoesCategoria(
            @Parameter(description = "ID do afiliado") @PathVariable String afiliadoId,
            @Parameter(description = "ID da categoria") @PathVariable String id,
            @ParameterObject Pageable page) {

        ProdutoDTO produto = ProdutoDTO.builder()
                .promocoes(Arrays.asList(PromocaoDTO.builder().build()))
                .build();
        List<PromocaoDTO> promocoes = Arrays.asList(produto)
                .stream()
                .flatMap(p -> p.getPromocoes().stream())
                .map(p -> {
                    p.add(linkTo(methodOn(ProdutoController.class).getId(afiliadoId, p.getId())).withSelfRel());
                    p.add(linkTo(methodOn(ProdutoController.class).alerta(afiliadoId, p.getId(), "http://localhost:8080/callback")).withSelfRel());
                    p.add(linkTo(methodOn(ProdutoController.class).getRanking(afiliadoId, p.getId())).withSelfRel());
                    p.add(linkTo(methodOn(ProdutoController.class).getAlertas(afiliadoId, p.getId(), page)).withSelfRel());
                    return p;
                }).toList();

        CollectionModel<PromocaoDTO> collectionModel = CollectionModel.of(promocoes);
        collectionModel.add(linkTo(methodOn(ProdutoController.class).getPromocoesCategoria(afiliadoId, id, page)).withSelfRel());

        return ResponseEntity.ok(new PageImpl<>(collectionModel.getContent().stream().toList()));
    }

    @Operation(summary = "Obter ranking de produtos por id de categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ranking de produtos encontrados",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RankingDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Parâmetro fornecido inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content),
    })
    @GetMapping("/v1/{afiliadoId}/produto/categoria/{id}/ranking")
    public ResponseEntity<Page<RankingDTO>> getCategoriaRanking(
            @Parameter(description = "ID do afiliado") @PathVariable String afiliadoId,
            @Parameter(description = "ID da categoria") @PathVariable String id,
            @Parameter(description = "Quantidade de itens retornados no ranking") @RequestParam Integer quantidade,
            @ParameterObject Pageable page) {

        ProdutoDTO produto = ProdutoDTO.builder()
                .promocoes(Arrays.asList(PromocaoDTO.builder().build()))
                .build();
        List<ProdutoDTO> produtos = Arrays.asList(produto)
                .stream()
                .map(p -> {
                    p.add(linkTo(methodOn(ProdutoController.class).getId(afiliadoId, p.getId())).withSelfRel());
                    p.add(linkTo(methodOn(ProdutoController.class).alerta(afiliadoId, p.getId(), "http://localhost:8080/callback")).withSelfRel());
                    p.add(linkTo(methodOn(ProdutoController.class).getAlertas(afiliadoId, p.getId(), page)).withSelfRel());
                    p.add(linkTo(methodOn(ProdutoController.class).getPromocoes(afiliadoId, p.getId(), page)).withSelfRel());
                    return p;
                }).toList();

        CollectionModel<RankingDTO> collectionModel = CollectionModel.of(produtos.stream().map(ProdutoDTO::getRanking).toList());
        collectionModel.add(linkTo(methodOn(ProdutoController.class).getCategoriaRanking(afiliadoId, id, quantidade, page)).withSelfRel());

        return ResponseEntity.ok(new PageImpl<>(collectionModel.getContent().stream().toList()));
    }

    @Operation(summary = "Obter alertas de produtos por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alertas de produtos encontrados",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AlertaDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Parâmetro fornecido inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content),
    })
    @GetMapping("/v1/{afiliadoId}/produto/{id}/alertas")
    public ResponseEntity<Page<AlertaDTO>> getAlertas(
            @Parameter(description = "ID do afiliado") @PathVariable String afiliadoId,
            @Parameter(description = "ID do produto") @PathVariable String id,
            @ParameterObject Pageable page) {

        ProdutoDTO produto = ProdutoDTO.builder()
                .alertas(Arrays.asList(AlertaDTO.builder().build()))
                .build();
        List<AlertaDTO> promocoes = Arrays.asList(produto)
                .stream()
                .flatMap(p -> p.getAlertas().stream())
                .map(p -> {
                    p.add(linkTo(methodOn(ProdutoController.class).getId(afiliadoId, p.getId())).withSelfRel());
                    p.add(linkTo(methodOn(ProdutoController.class).cancelarAlerta(afiliadoId, p.getId())).withSelfRel());
                    return p;
                }).toList();

        CollectionModel<AlertaDTO> collectionModel = CollectionModel.of(promocoes);
        collectionModel.add(linkTo(methodOn(ProdutoController.class).getAlertas(afiliadoId, id, page)).withSelfRel());

        return ResponseEntity.ok(new PageImpl<>(collectionModel.getContent().stream().toList()));
    }

    @Operation(summary = "Obter ranking de um produto por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RankingDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Parâmetro fornecido inválido", content = @Content),

            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content),
    })
    @GetMapping("/v1/{afiliadoId}/produto/{id}/ranking")
    public ResponseEntity<RankingDTO> getRanking(
            @Parameter(description = "ID do afiliado") @PathVariable String afiliadoId,
            @Parameter(description = "ID do produto") @PathVariable String id) {

        ProdutoDTO produto = ProdutoDTO.builder()
                .ranking(RankingDTO.builder().build())
                .build();
        produto.getRanking().add(linkTo(methodOn(ProdutoController.class).getId(afiliadoId, id)).withSelfRel());
        produto.add(linkTo(methodOn(ProdutoController.class).alerta(afiliadoId, id, "http://localhost:8080/callback")).withSelfRel());
        produto.add(linkTo(methodOn(ProdutoController.class).getAlertas(afiliadoId, id, PageRequest.of(0, 10))).withSelfRel());
        produto.add(linkTo(methodOn(ProdutoController.class).getPromocoes(afiliadoId, id, PageRequest.of(0, 10))).withSelfRel());

        return ResponseEntity.ok(produto.getRanking());
    }

    @Operation(summary = "Obter um produto por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Parâmetro fornecido inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content),
    })
    @GetMapping("/v1/{afiliadoId}/produto/{id}")
    public ResponseEntity<ProdutoDTO> getId(
            @Parameter(description = "ID do afiliado") @PathVariable String afiliadoId,
            @Parameter(description = "ID do produto") @PathVariable String id) {

        ProdutoDTO produto = ProdutoDTO.builder().build();
        produto.add(linkTo(methodOn(ProdutoController.class).getId(afiliadoId, id)).withSelfRel());
        produto.add(linkTo(methodOn(ProdutoController.class).getRanking(afiliadoId, id)).withSelfRel());
        produto.add(linkTo(methodOn(ProdutoController.class).getAlertas(afiliadoId, id, PageRequest.of(0, 10))).withSelfRel());
        produto.add(linkTo(methodOn(ProdutoController.class).getPromocoes(afiliadoId, id, PageRequest.of(0, 10))).withSelfRel());
        produto.add(linkTo(methodOn(ProdutoController.class).alerta(afiliadoId, id, "http://localhost:8080/callback")).withSelfRel());

        return ResponseEntity.ok(produto);

    }

    @Operation(summary = "Observar alertas de desconto de um produto por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AlertaDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Parâmetro fornecido inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content),
    })
    @PostMapping("/v1/{afiliadoId}/produto/{id}/alerta/callback/{url}")
    public ResponseEntity<AlertaDTO> alerta(
            @Parameter(description = "ID do afiliado") @PathVariable String afiliadoId,
            @Parameter(description = "ID do produto") @PathVariable String id,
            @Parameter(description = "URL de callback que será invocada quando houver alerta de desconto") @PathVariable String url) {

        AlertaDTO alerta = AlertaDTO.builder().build();
        alerta.add(linkTo(methodOn(ProdutoController.class).cancelarAlerta(afiliadoId, id)).withSelfRel());

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "adicionar produto ao catálogo do afiliado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto associado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Parâmetro fornecido inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content),
    })
    @PostMapping("/v1/{afiliadoId}/produto/{id}/adicionar")
    public ResponseEntity<ProdutoDTO> adicionar(
            @Parameter(description = "ID do afiliado") @PathVariable String afiliadoId,
            @Parameter(description = "ID do produto") @PathVariable String id) {

        ProdutoDTO produto = ProdutoDTO.builder().build();
        produto.add(linkTo(methodOn(ProdutoController.class).getId(afiliadoId, id)).withSelfRel());

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remover produto do catálogo de ao afiliado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto desassociado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AlertaDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Parâmetro fornecido inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content),
    })
    @DeleteMapping("/v1/{afiliadoId}/produto/{id}/remover")
    public ResponseEntity<String> remover(
            @Parameter(description = "ID do afiliado") @PathVariable String afiliadoId,
            @Parameter(description = "ID do produto") @PathVariable String id) {
        return ResponseEntity.ok(id);
    }

    @Operation(summary = "Remover alerta de desconto de um produto por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AlertaDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Parâmetro fornecido inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content),
    })
    @DeleteMapping("/v1/{afiliadoId}/produto/alerta/{id}")
    public ResponseEntity<String> cancelarAlerta(
            @Parameter(description = "ID do afiliado") @PathVariable String afiliadoId,
            @Parameter(description = "ID do produto") @PathVariable String id) {
        return ResponseEntity.ok(id);
    }
}
