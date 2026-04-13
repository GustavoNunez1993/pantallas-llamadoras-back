package com.datalock.datalock.authentication.entities;

import com.datalock.datalock.utils.infra.BaseDbModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserJpaModel extends BaseDbModel implements UserDetails {

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "apellido", length = 100, nullable = false)
    private String apellido;

    @Column(name = "email", unique = true, nullable = false, length = 75)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "rol", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "foto_url")
    private String fotoUrl;

    @Column(name = "foto_filename")
    private String fotoFilename;


    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<UsuarioSeccionJpaModel> historialSecciones;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}