import { Component } from '@angular/core';
import {Tab, TabList, TabPanels, Tabs, TabPanel} from 'primeng/tabs';
import {ContaTableComponent} from '@pag/conta/conta-table/conta-table.component';
import {ContaFormComponent} from '@pag/conta/conta-form/conta-form.component';
import {TipoContaComponent} from '@pag/conta/tipo-conta/tipo-conta.component';

@Component({
  selector: 'app-conta',
  imports: [
    Tabs,
    TabList,
    Tab,
    TabPanels,
    TabPanel,
    ContaTableComponent,
    ContaFormComponent,
    TipoContaComponent
  ],
  templateUrl: './conta.component.html',
  styleUrl: './conta.component.css'
})
export class ContaComponent {
  value: number = 0;
}
