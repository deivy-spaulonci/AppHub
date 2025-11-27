import { Routes } from '@angular/router';
import {HomeComponent} from '@pag/home/home.component';
import {DespesaComponent} from '@pag/despesa/despesa.component';
import {FornecedorComponent} from '@pag/fornecedor/fornecedor.component';
import {FornecedorTableComponent} from '@pag/fornecedor/fornecedor-table/fornecedor-table.component';
import {FornecedorFormComponent} from '@pag/fornecedor/fornecedor-form/fornecedor-form.component';
import {DespesaTableComponent} from '@pag/despesa/despesa-table/despesa-table.component';
import {DespesaFormComponent} from '@pag/despesa/despesa-form/despesa-form.component';
import {ContaTableComponent} from '@pag/conta/conta-table/conta-table.component';
import {ContaFormComponent} from '@pag/conta/conta-form/conta-form.component';
import {ParametrosComponent} from '@pag/parametros/parametros.component';
import {DocumentoComponent} from '@pag/documento/documento.component';
import {ContaComponent} from '@pag/conta/conta.component';
import {LoginComponent} from '@pag/login/login.component';
import {TipoConta} from '@model/tipo-conta';
import {TipoContaComponent} from '@pag/conta/tipo-conta/tipo-conta.component';
import {AuthGuard} from './security/AuthGuard';
import {LogoutComponent} from '@pag/logout/logout.component';

export const routes: Routes = [
  {path:'login', component: LoginComponent},
  {path:'home', component: HomeComponent, canActivate: [AuthGuard]},
  {path:'despesa', component:DespesaComponent,
    children: [
      {path: '',redirectTo: 'despesa-table',pathMatch: 'full'},
      {path:'despesa-table', component:DespesaTableComponent, canActivate: [AuthGuard]},
      {path:'despesa-form', component:DespesaFormComponent, canActivate: [AuthGuard]},
    ], canActivate: [AuthGuard]
  },
  {path:'fornecedor', component:FornecedorComponent, canActivate: [AuthGuard]},
  {path:'fornecedor-table', component:FornecedorTableComponent, canActivate: [AuthGuard]},
  {path:'fornecedor-form', component:FornecedorFormComponent, canActivate: [AuthGuard]},
  {path:'parametros', component:ParametrosComponent, canActivate: [AuthGuard]},
  {path:'documentos', component:DocumentoComponent, canActivate: [AuthGuard]},
  {path:'conta', component:ContaComponent,
    children: [
      {path: '',redirectTo: 'conta-table',pathMatch: 'full'},
      {path:'conta-table', component:ContaTableComponent, canActivate: [AuthGuard]},
      {path:'conta-form', component:ContaFormComponent, canActivate: [AuthGuard]},
      {path:'tipo-conta', component:TipoContaComponent, canActivate: [AuthGuard]},
    ], canActivate: [AuthGuard]
  },
  {path:'logout', component: LogoutComponent},
  { path: '',   redirectTo: '/home', pathMatch: 'full' },
];
