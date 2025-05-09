import { Component } from '@angular/core';
import {Tab, TabList, TabPanel, TabPanels, Tabs} from "primeng/tabs";
import {Toolbar} from "primeng/toolbar";
import {DespesaTableComponent} from './despesa-table/despesa-table.component';
import {DespesaFormComponent} from './despesa-form/despesa-form.component';

@Component({
  selector: 'app-despesa',
  imports: [
    Tab,
    TabList,
    TabPanel,
    TabPanels,
    Tabs,
    Toolbar,
    DespesaTableComponent,
    DespesaFormComponent
  ],
  templateUrl: './despesa.component.html',
  styleUrl: './despesa.component.css'
})
export class DespesaComponent {
  value: number = 0;
}
