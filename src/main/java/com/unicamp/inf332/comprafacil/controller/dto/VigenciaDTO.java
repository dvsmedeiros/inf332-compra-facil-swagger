package com.unicamp.inf332.comprafacil.controller.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VigenciaDTO {
    private LocalDateTime inicio;
    private LocalDateTime fim;
}
