import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8081/auth/login';
  private TOKEN_KEY = 'jwt_token'; // Defina uma chave constante

  constructor(private http: HttpClient) {}

  login(credentials: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, credentials).pipe(
      tap(response => {
        // Armazena o token (a chave deve ser a mesma na resposta do Spring)
        localStorage.setItem('jwt_token', response.token);
      })
    );
  }

  logout(): Observable<any>{
    localStorage.removeItem('jwt_token');
    return of(null);
  }

  isLoggedIn(): boolean {
    // Verifica se o token existe e não está expirado (validação simples)
    const token = localStorage.getItem('jwt_token');
    return !!token;
    // Em projetos reais, você faria uma checagem se o token expirou aqui.
  }

  /**
   * Retorna o token JWT armazenado no localStorage.
   * Usado principalmente pelo TokenInterceptor.
   */
  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }
}
