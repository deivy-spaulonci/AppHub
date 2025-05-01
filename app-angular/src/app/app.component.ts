import {Component, OnInit} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {ButtonModule} from 'primeng/button';
import {MenuModule} from 'primeng/menu';
import {MenuItem, MessageService} from 'primeng/api';
import {MenubarModule} from 'primeng/menubar';
import {CardModule} from 'primeng/card';
import {Toolbar} from 'primeng/toolbar';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,ButtonModule,MenuModule,MenubarModule,CardModule,Toolbar],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  standalone: true,
})
export class AppComponent implements OnInit {

  items: MenuItem[] | undefined;

  ngOnInit() {
    this.items = [
      {label: 'Home',icon: 'pi pi-home', routerLink:'home'},
      {label: 'Documentos',icon: 'pi pi-file',
        items: [
          {label: 'Documentos',icon: 'pi pi-file', routerLink:'documentos'},
          {label: 'Tipo Documentos',icon: 'pi pi-tag'},
        ]
      },
      {label: 'Financeiro',icon: 'pi pi-dollar',
        items: [
          {label: 'Despesa',icon: 'pi pi-money-bill',
            items: [
              {label: 'Cadastro',icon: 'pi pi-plus', routerLink:'despesa-form'},
              {label: 'Consulta',icon: 'pi pi-bars', routerLink:'despesa-table'},
              {label: 'Tipo Despesa',icon: 'pi pi-tag'}
            ]
          },
          {label: 'Contas',icon: 'pi pi-barcode',
            items: [
              {label: 'Cadastro',icon: 'pi pi-plus', routerLink:'conta-form'},
              {label: 'Consulta',icon: 'pi pi-bars', routerLink:'conta-table'},
              {label: 'Tipo Conta',icon: 'pi pi-tag'}
            ]
          },
          {label: 'Fornecedor',icon: 'pi pi-box',
            items: [
              {label: 'Cadastro',icon: 'pi pi-plus', routerLink:'fornecedor-form'},
              {label: 'Consulta',icon: 'pi pi-bars', routerLink:'fornecedor-table'}
            ]
          }
        ]
      },
      {label: 'Parametros',icon: 'pi pi-cog', routerLink:'parametros'},
    ]
  }

}
