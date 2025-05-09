import { Routes } from '@angular/router';
import {HomeComponent} from './pag/home/home.component';
import {DespesaComponent} from './pag/despesa/despesa.component';
import {FornecedorComponent} from './pag/fornecedor/fornecedor.component';
import {FornecedorTableComponent} from './pag/fornecedor/fornecedor-table/fornecedor-table.component';
import {FornecedorFormComponent} from './pag/fornecedor/fornecedor-form/fornecedor-form.component';
import {DespesaTableComponent} from './pag/despesa/despesa-table/despesa-table.component';
import {DespesaFormComponent} from './pag/despesa/despesa-form/despesa-form.component';
import {ContaTableComponent} from './pag/conta/conta-table/conta-table.component';
import {ContaFormComponent} from './pag/conta/conta-form/conta-form.component';
import {ParametrosComponent} from './pag/parametros/parametros.component';
import {DocumentoComponent} from './pag/documento/documento.component';
import {ContaComponent} from './pag/conta/conta.component';

export const routes: Routes = [
  {path:'home', component: HomeComponent},
  {path:'despesa', component:DespesaComponent},
  {path:'fornecedor', component:FornecedorComponent},
  {path:'fornecedor-table', component:FornecedorTableComponent},
  {path:'fornecedor-form', component:FornecedorFormComponent},
  {path:'despesa-table', component:DespesaTableComponent},
  {path:'despesa-form', component:DespesaFormComponent},
  {path:'conta-table', component:ContaTableComponent},
  {path:'conta-form', component:ContaFormComponent},
  {path:'parametros', component:ParametrosComponent},
  {path:'documentos', component:DocumentoComponent},
  {path:'conta', component:ContaComponent},

  { path: '',   redirectTo: '/home', pathMatch: 'full' },
];
