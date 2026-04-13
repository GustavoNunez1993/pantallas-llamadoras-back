package com.datalock.datalock.authentication.security.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Schema(description = "Estructura de respuesta paginada estándar usada en los endpoints del sistema")
public class PageResponse<T> {

    @Schema(description = "Lista de elementos devueltos en la página actual")
    private List<T> content;

    @Schema(description = "Número de la página actual (comienza desde 0)")
    private int pageNumber;

    @Schema(description = "Cantidad de elementos que contiene cada página")
    private int pageSize;

    @Schema(description = "Cantidad total de elementos disponibles en la consulta")
    private long totalElements;

    @Schema(description = "Cantidad total de páginas disponibles")
    private int totalPages;

    @Schema(description = "Indica si la página actual es la última")
    private boolean last;

    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.last = page.isLast();
    }
}
