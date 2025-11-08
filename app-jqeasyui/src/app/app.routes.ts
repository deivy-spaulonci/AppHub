import { Routes } from '@angular/router';
import {Home} from './view/home/home';
import {Fornecedor} from './view/fornecedor/fornecedor';

export const routes: Routes = [
    {path:'home', component: Home},
    {path:'fornecedor', component: Fornecedor},
   { path: '',   redirectTo: '/home', pathMatch: 'full' },
];
