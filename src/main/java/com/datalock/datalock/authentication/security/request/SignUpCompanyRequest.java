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
public class SignUpCompanyRequest {

    private String razonSocial;
    private String ruc;
    private String direccion;
    private String telefono;
    private String email;
    private String password;
    private String passwordValidate;
    private Double latitud;
    private Double logitud;
    private Role role;
}
