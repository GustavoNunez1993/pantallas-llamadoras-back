package com.datalock.datalock.utils.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseDTO<T> {

    private boolean success;
    private String message;
    private T data;
}

