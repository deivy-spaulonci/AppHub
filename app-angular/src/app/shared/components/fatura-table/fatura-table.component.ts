import {Component, Input} from '@angular/core';
import {CurrencyPipe, DatePipe, NgIf} from '@angular/common';
import {TableModule} from 'primeng/table';
import {Fatura} from '../../../model/fatura';
import {Tooltip} from 'primeng/tooltip';

@Component({
  selector: 'app-fatura-table',
  imports: [
    CurrencyPipe,
    TableModule,
    DatePipe,
    Tooltip,
    NgIf
  ],
  templateUrl: './fatura-table.component.html',
  styleUrl: './fatura-table.component.css'
})
export class FaturaTableComponent {
  @Input() faturas:Fatura[]=[];
  @Input() sHeight:string = '320px';
  @Input() rmBaskt:boolean = false;


  delFatura(event: Event, fatura:Fatura){
    this.faturas = this.faturas.filter(obj => {return obj !== fatura});
  }
}
