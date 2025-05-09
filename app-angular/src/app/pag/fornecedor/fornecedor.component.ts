import { Component } from '@angular/core';
import {Toolbar} from 'primeng/toolbar';
import {Tab, TabList, TabPanel, TabPanels, Tabs} from 'primeng/tabs';
import {FornecedorTableComponent} from './fornecedor-table/fornecedor-table.component';
import {FornecedorFormComponent} from './fornecedor-form/fornecedor-form.component';

@Component({
  selector: 'app-fornecedor',
  imports: [
    Toolbar,
    Tab,
    TabList,
    TabPanel,
    TabPanels,
    Tabs,
    FornecedorTableComponent,
    FornecedorFormComponent
  ],
  templateUrl: './fornecedor.component.html',
  styleUrl: './fornecedor.component.css'
})
export class FornecedorComponent {
  value: number = 0;
}
