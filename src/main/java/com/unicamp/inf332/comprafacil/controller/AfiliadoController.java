package com.unicamp.inf332.comprafacil.controller;

import com.unicamp.inf332.comprafacil.controller.dto.AfiliadoDTO;
import com.unicamp.inf332.comprafacil.controller.dto.EnderecoDTO;
import com.unicamp.inf332.comprafacil.controller.dto.IdDTO;
import com.unicamp.inf332.comprafacil.repository.AfiliadoRepository;
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
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Afiliado", description = "Afiliado Resource")
@RestController
public class AfiliadoController {

    private AfiliadoRepository repository;

    AfiliadoController(){
         repository = new AfiliadoRepository();
         repository.findAll().forEach(a -> System.out.println(a));
    }

    @Operation(summary = "Obter uma lista de afiliados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "afiliados encontrados",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AfiliadoDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Parâmetros fornecidos são inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "afiliados não encontrados", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content),
    })
    @GetMapping("/v1/afiliado")
    public ResponseEntity<Page<AfiliadoDTO>> get(
            @Parameter(description = "CNPJ do afiliado") @RequestParam(required = false) String cnpj,
            @Parameter(description = "Nome do afiliado") @RequestParam(required = false) String nome,
            @Parameter(description = "Email do afiliado") @RequestParam(required = false) String email,
            @ParameterObject Pageable page) {

        try {
            List<AfiliadoDTO> afiliados = repository.findByFilter(cnpj, nome, email);

            CollectionModel<AfiliadoDTO> collectionModel = CollectionModel.of(afiliados);
            collectionModel.getContent().forEach(a -> {
                a.add(linkTo(methodOn(AfiliadoController.class).getId(a.getId())).withSelfRel());
                a.add(linkTo(methodOn(AfiliadoController.class).put(a.getId(), a)).withRel("update"));
                a.add(linkTo(methodOn(AfiliadoController.class).delete(a.getId())).withRel("delete"));
            });
            collectionModel.add(linkTo(methodOn(AfiliadoController.class).get(cnpj, nome, email, page)).withSelfRel());

            return ResponseEntity.ok(new PageImpl<>(afiliados));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Obter um afiliado por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "afiliado encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AfiliadoDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Parâmetro fornecido inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "afiliado não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content),
    })
    @GetMapping("/v1/afiliado/{id}")
    public ResponseEntity<AfiliadoDTO> getId(
            @Parameter(description = "ID do afiliado") @PathVariable String id) {
        try {
            Optional<AfiliadoDTO> byId = repository.findById(id);

            if (!byId.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            AfiliadoDTO afiliado = byId.get();

            //afiliado.add(linkTo(methodOn(AfiliadoController.class).put(afiliado.getId(), afiliado)).withRel("update"));
            //afiliado.add(linkTo(methodOn(AfiliadoController.class).delete(afiliado.getId())).withRel("delete"));
            //afiliado.add(linkTo(methodOn(AfiliadoController.class).get(afiliado.getCnpj(), afiliado.getNome(), afiliado.getEmail(), PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION));

            return ResponseEntity.ok(afiliado);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Criar um afiliado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "afiliado criado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AfiliadoDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Parâmetros fornecidos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content),
    })
    @PostMapping("/v1/afiliado")
    public ResponseEntity<AfiliadoDTO> post(
            @Parameter(description = "Dados do afiliado") @RequestBody AfiliadoDTO afiliado) {

        try {
            AfiliadoDTO created = AfiliadoDTO.builder()
                    .id(UUID.randomUUID().toString())
                    .nome(Optional.ofNullable(afiliado.getNome()).orElse(null))
                    .email(Optional.ofNullable(afiliado.getEmail()).orElse(null))
                    .cnpj(Optional.ofNullable(afiliado.getCnpj()).orElse(null))
                    .endereco(EnderecoDTO.builder()
                            .cep(Optional.ofNullable(afiliado.getEndereco().getCep()).orElse(null))
                            .logradouro(Optional.ofNullable(afiliado.getEndereco().getLogradouro()).orElse(null))
                            .numero(Optional.ofNullable(afiliado.getEndereco().getNumero()).orElse(null))
                            .cidade(Optional.ofNullable(afiliado.getEndereco().getCidade()).orElse(null))
                            .estado(Optional.ofNullable(afiliado.getEndereco().getEstado()).orElse(null))
                            .build())
                    .build();

            repository.save(created);

            created.add(linkTo(methodOn(AfiliadoController.class).getId(afiliado.getId())).withSelfRel());
            created.add(linkTo(methodOn(AfiliadoController.class).put(afiliado.getId(), afiliado)).withRel("update"));
            created.add(linkTo(methodOn(AfiliadoController.class).delete(afiliado.getId())).withRel("delete"));
            created.add(linkTo(methodOn(AfiliadoController.class).get(afiliado.getCnpj(), afiliado.getNome(), afiliado.getEmail(), PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION));

            return ResponseEntity.created(linkTo(methodOn(AfiliadoController.class).getId(created.getId())).toUri()).body(created);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Atualizar um afiliado por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "afiliado atualizado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AfiliadoDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Parâmetros fornecidos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "afiliado não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content),
    })
    @PutMapping("/v1/afiliado/{id}")
    public ResponseEntity<AfiliadoDTO> put(
            @Parameter(description = "ID do afiliado") @PathVariable String id,
            @Parameter(description = "Dados do afiliado à serem atualizados") @RequestBody AfiliadoDTO afiliado) {

        try {

            afiliado.setId(id);

            Optional<AfiliadoDTO> updated = repository.update(afiliado);
            if (!updated.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            updated.get().add(linkTo(methodOn(AfiliadoController.class).getId(id)).withSelfRel());
            updated.get().add(linkTo(methodOn(AfiliadoController.class).delete(id)).withRel("delete"));
            updated.get().add(linkTo(methodOn(AfiliadoController.class).get(updated.get().getCnpj(), updated.get().getNome(), updated.get().getEmail(), PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION));

            return ResponseEntity.ok(updated.get());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }

    @Operation(summary = "Remover um afiliado por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "afiliado removido", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Parâmetros fornecidos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "afiliado não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content),
    })
    @DeleteMapping("/v1/afiliado/{id}")
    public ResponseEntity<IdDTO> delete(
            @Parameter(description = "ID do afiliado") @PathVariable String id) {
        Optional<IdDTO> deleted = repository.delete(id);
        if (!deleted.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deleted.get());
    }
}
