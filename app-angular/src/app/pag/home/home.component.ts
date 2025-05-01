import { Component } from '@angular/core';
import {CardModule} from 'primeng/card';
import {MessageModule} from 'primeng/message';
import {DefaultService} from '../../service/default.service';
import {Observable} from 'rxjs';
import {FormsModule} from '@angular/forms';
import {CurrencyPipe} from '@angular/common';
import {Fieldset} from 'primeng/fieldset';

@Component({
  selector: 'app-home',
  imports: [CardModule, MessageModule, FormsModule, CurrencyPipe, Fieldset],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  standalone: true
})
export class HomeComponent {

  totalDespesa$!: number;
  totalConta$!: number;
  totalContaAvencer$!:number;
  totalContaAberto$!:number;
  totalContaHoje$!:number;
  totalContaAtradado$!:number;

  constructor(private defaultService: DefaultService) {
  }

  ngOnInit(): void {
    this.defaultService.get('despesa/valorTotal').subscribe(res =>{
      this.totalDespesa$ = res;
    });

    this.defaultService.get('conta/valorTotal').subscribe(res =>{
      this.totalConta$ = res;
    });

    this.defaultService.get('conta/valorTotal?contaStatus=PAGO').subscribe(res =>{
      this.totalContaAvencer$ = res;
    });

    this.defaultService.get('conta/valorTotal?contaStatus=ABERTO').subscribe(res =>{
      this.totalContaAberto$ = res;
    });

    this.defaultService.get('conta/valorTotal?contaStatus=HOJE').subscribe(res =>{
      this.totalContaHoje$ = res;
    });

    this.defaultService.get('conta/valorTotal?contaStatus=ATRASADO').subscribe(res =>{
      this.totalContaAtradado$ = res;
    });

  }
}
