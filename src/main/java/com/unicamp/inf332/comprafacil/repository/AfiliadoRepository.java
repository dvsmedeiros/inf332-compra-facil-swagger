package com.unicamp.inf332.comprafacil.repository;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.unicamp.inf332.comprafacil.controller.dto.AfiliadoDTO;
import com.unicamp.inf332.comprafacil.controller.dto.EnderecoDTO;
import com.unicamp.inf332.comprafacil.controller.dto.IdDTO;
import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class AfiliadoRepository {

    private List<AfiliadoDTO> afiliados = new ArrayList<>();

    public AfiliadoRepository() {
        populate();
    }

    private void populate() {
        Faker fakerBR = Faker.instance(new Locale("pt-BR"));
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("pt-BR"), new RandomService());
        for (int i = 0; i < 10; i++) {
            AfiliadoDTO afiliado = AfiliadoDTO.builder()
                    .id(UUID.randomUUID().toString())
                    .nome(fakerBR.company().name())
                    .email(fakerBR.company().url())
                    .cnpj(fakeValuesService.regexify("[0-9]{2}\\.[0-9]{3}\\.[0-9]{3}/0001-[0-9]{2}"))
                    .endereco(EnderecoDTO.builder()
                            .cep(fakerBR.address().zipCode())
                            .logradouro(fakerBR.address().streetName())
                            .numero(fakerBR.address().buildingNumber())
                            .cidade(fakerBR.address().cityName())
                            .estado(fakerBR.address().state())
                            .build())
                    .build();
            afiliados.add(afiliado);
        }
    }

    public List<AfiliadoDTO> findAll() {
        return afiliados;
    }

    public List<AfiliadoDTO> findByFilter(String cnpj, String nome, String email) {

        return afiliados.stream()
                .filter(a -> cnpj == null || a.getCnpj().equals(cnpj))
                .filter(a -> nome == null || a.getNome().contains(nome.toLowerCase()))
                .filter(a -> email == null || a.getEmail().contains(email.toLowerCase()))
                .toList();
    }

    public Optional<AfiliadoDTO> findById(String id) {
        return afiliados.stream()
                .filter(a -> id != null && id.equals(a.getId()))
                .findFirst();
    }

    public AfiliadoDTO save(AfiliadoDTO created) {
        if (created == null) {
            created = AfiliadoDTO.builder().build();
        }
        if (created.getId() == null || created.getId().isEmpty()) {
            created.setId(UUID.randomUUID().toString());
        }
        afiliados.add(created);
        return created;
    }

    public Optional<AfiliadoDTO> update(AfiliadoDTO afiliado) {
        if (afiliado.getEndereco() == null) {
            afiliado.setEndereco(EnderecoDTO.builder().build());
        }
        return findById(afiliado.getId())
                .map(a -> AfiliadoDTO.builder()
                        .id(a.getId())
                        .nome(Optional.ofNullable(afiliado.getNome()).orElse(a.getNome()))
                        .email(Optional.ofNullable(afiliado.getEmail()).orElse(a.getEmail()))
                        .cnpj(Optional.ofNullable(afiliado.getCnpj()).orElse(a.getCnpj()))
                        .endereco(EnderecoDTO.builder()
                                .cep(Optional.ofNullable(afiliado.getEndereco().getCep()).orElse(a.getEndereco().getCep()))
                                .logradouro(Optional.ofNullable(afiliado.getEndereco().getLogradouro()).orElse(a.getEndereco().getLogradouro()))
                                .numero(Optional.ofNullable(afiliado.getEndereco().getNumero()).orElse(a.getEndereco().getNumero()))
                                .cidade(Optional.ofNullable(afiliado.getEndereco().getCidade()).orElse(a.getEndereco().getCidade()))
                                .bairro(Optional.ofNullable(afiliado.getEndereco().getBairro()).orElse(a.getEndereco().getBairro()))
                                .estado(Optional.ofNullable(afiliado.getEndereco().getEstado()).orElse(a.getEndereco().getEstado()))
                                .build())
                        .build());
    }

    public Optional<IdDTO> delete(String id) {
        return findById(id).map(a -> {
            afiliados.remove(a);
            return IdDTO.builder().id(id).build();
        });
    }
}
