import {Component, OnInit} from '@angular/core';
import {Router, RouterLink, RouterOutlet} from '@angular/router';
import {Tab, TabList, Tabs} from 'primeng/tabs';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Tabs, TabList, Tab, RouterLink, NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  standalone: true,
})
export class AppComponent implements OnInit {

  tabs = [
    { route: 'home', label: 'Dashboard', icon: 'pi pi-home' },
    { route: 'conta', label: 'Contas', icon: 'pi pi-barcode' },
    { route: 'despesa', label: 'Despesas', icon: 'pi pi-money-bill' },
    { route: 'fornecedor', label: 'Fornecedores', icon: 'pi pi-box' },
    { route: 'documentos', label: 'Documentos', icon: 'pi pi-file-o' },
    { route: 'logout', label: 'Logout', icon: 'pi pi-sign-out' },
  ];

  constructor(private router: Router) {
  }
  ngOnInit() {
  }
  isLoginPage(): boolean {
    // 💡 Método 1: Verifica o segmento final da URL
    // Se a sua rota de login for exatamente '/login'
    return this.router.url.includes('/login');

    // 💡 Método 2: Verifica o caminho exato
    // return this.router.url === '/login';
  }
}
