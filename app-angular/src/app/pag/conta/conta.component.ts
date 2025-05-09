import { Component } from '@angular/core';
import {Tab, TabList, TabPanels, Tabs,TabPanel} from 'primeng/tabs';
import {ContaTableComponent} from './conta-table/conta-table.component';
import {ContaFormComponent} from './conta-form/conta-form.component';
import {Toolbar} from 'primeng/toolbar';

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
    Toolbar,
  ],
  templateUrl: './conta.component.html',
  styleUrl: './conta.component.css'
})
export class ContaComponent {
  value: number = 0;

}
