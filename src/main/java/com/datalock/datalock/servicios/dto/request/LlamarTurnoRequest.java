package com.datalock.datalock.servicios.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LlamarTurnoRequest {

    @NotBlank
    private String moduloActual;

    @NotBlank
    private String pantallaDestino;
}