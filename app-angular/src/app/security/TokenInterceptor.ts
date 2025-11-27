import { Injectable } from '@angular/core';
import {HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import {AuthService} from '@service/AuthService';
import {Router} from '@angular/router';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService, private router: Router) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    const token = this.authService.getToken();
    // Verifica se o token existe
    if (token) {
      // ⚠️ Clona a requisição e adiciona o cabeçalho Authorization
      request = request.clone({
        setHeaders: {
          // O formato exigido pelo Spring Security
          Authorization: `Bearer ${token}`
        }
      });
    }

    return next.handle(request).pipe(
      // 1. CAPTURA DE ERRO: Observa o resultado da requisição
      catchError((error: HttpErrorResponse) => {

        // 2. VERIFICA O STATUS: Se for 401 (Não Autorizado) ou 403 (Proibido)
        if (error.status === 401 || error.status === 403) {

          // 3. AÇÃO: Assume que o token expirou ou é inválido
          console.log('Token expirado ou inválido. Realizando logout...');
          this.authService.logout(); // Remove o token do localStorage

          // 4. REDIRECIONAMENTO: Redireciona o usuário para a tela de login
          this.router.navigate(['/login'], { queryParams: { session: 'expired' } });

          // Opcional: Você pode adicionar uma notificação de "Sessão Expirada"
        }

        // 5. Propaga o erro para o componente que fez a chamada original
        return throwError(() => error);
      })
    );
  }
}
