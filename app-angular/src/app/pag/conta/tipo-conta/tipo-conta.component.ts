import {Component, OnInit} from '@angular/core';
import {TableModule} from 'primeng/table';
import {ConfirmationService, MessageService} from 'primeng/api';
import {DefaultService} from '../../../service/default.service';
import {TipoConta} from '../../../model/tipo-conta';
import {NgIf, NgStyle} from '@angular/common';

@Component({
  selector: 'app-tipo-conta',
  imports: [
    TableModule,
    NgIf,
    NgStyle,
  ],
  templateUrl: './tipo-conta.component.html',
  providers: [MessageService,ConfirmationService],
  styleUrl: './tipo-conta.component.css'
})
export class TipoContaComponent implements OnInit{

  tiposConta:TipoConta[]=[];

  constructor(private defaultService: DefaultService,
              private messageService: MessageService) {
  }


  ngOnInit(): void {
    this.defaultService.get('tipo-conta').subscribe({
      next: res =>{ this.tiposConta = res; },
      error: error => { this.messageService.add({ severity: 'error', summary: 'Error', detail: 'consulta de tipo de contas' }); },
      complete: () => {}
    });
  }

  rowStyle(ativo: boolean) {
    return !ativo ? {textDecoration: 'line-through'} : {textDecoration: 'none'};
  }
}
