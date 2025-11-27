package com.br.business.service;

import com.br.entity.UserApp;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
// Supondo que você tenha um repositório para suas entidades de usuário
import com.br.repository.UserRepository;// Sua classe de modelo de usuário

@Service // Marca a classe como um Bean do Spring
public class CustomUserDetailsService implements UserDetailsService {

    // Se estiver usando JPA, injete seu repositório aqui
    @Autowired
    private UserRepository userRepository;

    // Este é o único método que você precisa implementar
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. Lógica para buscar o usuário no banco de dados (usando seu repositório)
        UserApp userApp = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        // 2. Converta sua entidade de usuário (com.br.model.User) para um objeto UserDetails do Spring.
        // O Spring fornece uma implementação chamada org.springframework.security.core.userdetails.User

        return new org.springframework.security.core.userdetails.User(
                userApp.getUsername(),
                userApp.getPassword(), // A senha deve ser a senha já criptografada!
                userApp.getAuthorities() // As permissões/papéis do usuário (ROLE_ADMIN, ROLE_USER)
        );

        // Se a sua classe de modelo 'User' já implementar a interface UserDetails, você pode retornar 'user' diretamente.
    }
}
