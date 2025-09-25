import {Component, OnInit} from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {Tab, TabList, Tabs} from 'primeng/tabs';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Tabs, TabList, Tab, RouterLink],
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
    { route: 'documentos', label: 'Documentos', icon: 'pi pi-file-o' }
  ];

  ngOnInit() {
  }
}
