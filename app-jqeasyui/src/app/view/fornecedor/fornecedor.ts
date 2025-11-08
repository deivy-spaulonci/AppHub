import {Component, OnInit} from '@angular/core';
import {DefaultService} from '../../service/default.service';
import {DataGridComponent, GridColumnComponent} from 'ng-easyui';

@Component({
  selector: 'app-fornecedor',
  imports: [
    DataGridComponent,
    GridColumnComponent
  ],
  templateUrl: './fornecedor.html',
  styleUrl: './fornecedor.css'
})
export class Fornecedor implements OnInit{

  fornecedores:any[]=[];

  constructor(private defaultService: DefaultService) {
  }

  ngOnInit(): void {
    this.defaultService.get('fornecedor').subscribe({
      next: res =>{ this.fornecedores = res; },
      error: error => { },
      complete: () => {}
    });
  }
}
