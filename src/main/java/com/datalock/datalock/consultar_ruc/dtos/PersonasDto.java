package com.datalock.datalock.consultar_ruc.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PersonasDto {

    private String ruc;
    private String dv;
    private String nombre;
    private String estado;
    private String esFacturador;

    // ── Mapeo desde JsonNode ────────────────────────────────────────────────────
    public static PersonasDto fromJson(JsonNode cliente) {

        PersonasDto dto = new PersonasDto();

        // RUC y DV — viene como "4112669-6"
        if (cliente.has("ruc")) {
            String[] partes = cliente.get("ruc").asText().split("-");
            dto.ruc = partes[0];
            dto.dv  = partes.length > 1 ? partes[1] : "";
        }

        // Nombre — campo confirmado: "persona"
        if (cliente.has("persona")) dto.nombre = cliente.get("persona").asText();

        // Estado — campo confirmado: "estado"
        if (cliente.has("estado")) dto.estado = cliente.get("estado").asText();

        // Es facturador — campo confirmado: "esFacturador"
        if (cliente.has("esFacturador")) dto.esFacturador = cliente.get("esFacturador").asText();

        return dto;
    }
}