import { Component } from '@angular/core';
import {Tab, TabList, TabPanel, TabPanels, Tabs} from "primeng/tabs";
import {DespesaTableComponent} from './despesa-table/despesa-table.component';
import {DespesaFormComponent} from './despesa-form/despesa-form.component';
import {RouterLink, RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-despesa',
  imports: [
    Tab,
    TabList,
    TabPanel,
    TabPanels,
    Tabs,
    DespesaTableComponent,
    DespesaFormComponent,
    RouterOutlet,
    RouterLink
  ],
  templateUrl: './despesa.component.html',
  styleUrl: './despesa.component.css'
})
export class DespesaComponent {
  value: number = 0;
}
