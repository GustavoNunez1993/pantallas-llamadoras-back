package com.datalock.datalock.authentication.security.request;


import com.datalock.datalock.authentication.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String validatePassword;
    private Role role;
    private Boolean aceptTerms;
    private String empresaId;
}
