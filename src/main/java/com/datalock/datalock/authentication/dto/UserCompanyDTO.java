package com.datalock.datalock.authentication.dto;


import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserCompanyDTO {

    private UUID id;

    private String razonSocial;

    private String ruc;

    private String direccion;

    private String telefono;

    private String email;

    private String rol;

    private String fotoUrl;

    private String fotoFilename;

    private Boolean activo;


}
