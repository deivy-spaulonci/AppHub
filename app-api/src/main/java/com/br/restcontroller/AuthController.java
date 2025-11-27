package com.br.restcontroller;

import com.br.record.ErrorResponse;
import com.br.record.LoginRequest;
import com.br.record.LoginResponse;
import com.br.business.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest request) {
        try {
            // Tenta autenticar o usuário com as credenciais fornecidas
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

            // Se a autenticação foi bem-sucedida (não lançou exceção), gera o token
            final UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
            final String jwt = jwtService.generateToken(userDetails);

            return ResponseEntity.ok(new LoginResponse(jwt));

        } catch (AuthenticationException e) {
            if (e instanceof org.springframework.security.authentication.BadCredentialsException) {
                // A senha ou o nome de usuário estão errados
                String msg = "Senha ou usuário incorretos.";
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(msg, new java.util.Date().toString()));
            }
            // Para outros tipos de erro (ex: conta desabilitada ou bloqueada)
            else {
                String msg = "Não foi possível logar. Motivo: " + e.getMessage();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(msg, new java.util.Date().toString()));
            }
        }
    }
}
