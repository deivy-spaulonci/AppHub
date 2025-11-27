package com.br.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "USER_APP")
public class UserApp implements UserDetails {

    @Id
    private Long id;
    private String username; // Seu campo de login/email
    private String password; // A senha CRIPTOGRAFADA
    private String role;     // Ex: "ADMIN", "USER"

    // 1. Retorna as permissões/papéis do usuário (ROLE_ADMIN, ROLE_USER)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    // 2. Retorna a senha (já criptografada)
    @Override
    public String getPassword() {
        return password;
    }

    // 3. Retorna o nome de usuário (identificador único)
    @Override
    public String getUsername() {
        return username;
    }

    // 4. Indica se a conta não expirou (Geralmente true)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 5. Indica se a conta não está bloqueada (Geralmente true)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 6. Indica se as credenciais (senha) não expiraram (Geralmente true)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 7. Indica se o usuário está habilitado/ativo (Geralmente true)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
