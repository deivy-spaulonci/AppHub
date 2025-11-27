import {Component, OnInit} from '@angular/core';
import {Button} from 'primeng/button';
import {IftaLabel} from 'primeng/iftalabel';
import {InputText} from 'primeng/inputtext';
import {Password} from 'primeng/password';
import {Card} from 'primeng/card';
import { PasswordModule } from 'primeng/password';
import {AuthService} from '@service/AuthService';
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [
    Card,
    Button,
    IftaLabel,
    InputText,
    Password,
    PasswordModule,
    FormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
  // Objeto que armazena os dados do formulário
  credentials = {
    username: '',
    password: ''
  };

  // Variáveis de estado
  isLoading = false;
  errorMessage: string | null = null;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Se o usuário já estiver logado, redireciona para o dashboard
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/home']);
    }
  }

  /**
   * Método chamado ao submeter o formulário de login.
   */
  onSubmit(form: any): void {
    this.isLoading = true;
    this.errorMessage = null;

    this.authService.login(this.credentials).subscribe({
      next: (response) => {
        // Sucesso: Token recebido e armazenado (graças ao 'tap' no AuthService)
        this.isLoading = false;

        // Redireciona o usuário para a página principal ou dashboard
        this.router.navigate(['/home']);
      },
      error: (error: HttpErrorResponse) => {
        this.isLoading = false;

        // Trata o erro de autenticação (401 Unauthorized)
        if (error.status === 401 || error.status === 403) {
          // Se a API Spring Boot retornou a mensagem personalizada:
          if (error.error && error.error.message) {
            this.errorMessage = error.error.message;
          } else {
            // Mensagem genérica se a resposta JSON não tiver um campo 'message'
            this.errorMessage = 'Credenciais inválidas. Tente novamente.';
          }
        } else {
          this.errorMessage = 'Ocorreu um erro na comunicação com o servidor. Tente novamente mais tarde.';
          console.error('Erro de servidor:', error);
        }
      }
    });
  }
}
