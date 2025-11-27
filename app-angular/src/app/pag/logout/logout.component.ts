import {Component, OnInit} from '@angular/core';
import {AuthService} from '@service/AuthService';
import {Router} from '@angular/router';

@Component({
  selector: 'app-logout',
  imports: [],
  templateUrl: './logout.component.html',
  styleUrl: './logout.component.css'
})
export class LogoutComponent implements OnInit{

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.processarLogout();
  }

  // Crie um método dedicado para a lógica de logout/redirecionamento
  private processarLogout(): void {

    console.log('Iniciando processo de logout...');

    // 1. Inscreve-se no Observable de logout
    this.authService.logout().subscribe({

      next: () => {
        // Esta função é executada APENAS DEPOIS que o Observable de logout completa
        console.log('Logout local e/ou do servidor concluído.');
      },

      error: (err) => {
        // Trata erros de logout (ex: falha na chamada de API)
        console.error('Erro durante o logout, mas vamos redirecionar:', err);
      },

      complete: () => {
        // 2. Redirecionamento: Esta função é executada em caso de SUCESSO ou ERRO,
        // mas garante que a ação assíncrona foi TENTADA/CONCLUÍDA.
        this.router.navigate(['login']);
        console.log('Redirecionando para a página de login.');
      }
    });
  }

}
