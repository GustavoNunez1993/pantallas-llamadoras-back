package com.datalock.datalock.consultar_ruc.services;

import com.fasterxml.jackson.databind.JsonNode;

public interface ConsultarRucServices {

    JsonNode consultarRuc(String ruc);
}
