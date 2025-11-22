import { Component } from '@angular/core';
import {FornecedorTableComponent} from '@pag/fornecedor/fornecedor-table/fornecedor-table.component';
import {FornecedorFormComponent} from '@pag/fornecedor/fornecedor-form/fornecedor-form.component';

@Component({
  selector: 'app-fornecedor',
  imports: [
    FornecedorTableComponent,
    FornecedorFormComponent,
  ],
  templateUrl: './fornecedor.component.html',
  styleUrl: './fornecedor.component.css'
})
export class FornecedorComponent {
}
