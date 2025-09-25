import {Component, OnInit} from '@angular/core';
import {TableModule} from 'primeng/table';
import {ConfirmationService, MessageService} from 'primeng/api';
import {DefaultService} from '../../../service/default.service';
import {TipoConta} from '../../../model/tipo-conta';
import {CurrencyPipe, NgClass, NgIf, NgStyle} from '@angular/common';
import {Card} from 'primeng/card';
import {Message} from 'primeng/message';
import {Button, ButtonDirective} from 'primeng/button';
import {ConfirmDialog} from 'primeng/confirmdialog';
import {Tooltip} from 'primeng/tooltip';
import {Toast} from 'primeng/toast';
import {LoadingModalComponent} from '../../../shared/loading-modal/loading-modal.component';
import {InputText} from 'primeng/inputtext';
import {FormsModule} from '@angular/forms';
import {InputGroup} from 'primeng/inputgroup';
import {InputGroupAddon} from 'primeng/inputgroupaddon';
import {Checkbox} from 'primeng/checkbox';

@Component({
  selector: 'app-tipo-conta',
  imports: [
    TableModule,
    NgIf,
    NgStyle,
    Card,
    Button,
    ConfirmDialog,
    Tooltip,
    Toast,
    LoadingModalComponent,
    InputText,
    FormsModule,
    InputGroup,
    InputGroupAddon,
    ButtonDirective,
    Checkbox,
    NgClass
  ],
  templateUrl: './tipo-conta.component.html',
  providers: [MessageService,ConfirmationService],
  styleUrl: './tipo-conta.component.css'
})
export class TipoContaComponent implements OnInit{

  tiposConta:TipoConta[]=[];
  loading:boolean=false;

  tipos!: any[];
  clonedTipos: { [s: string]: any } = {};
  tipoCadastro: TipoConta = new TipoConta();

  constructor(private defaultService: DefaultService,
              private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.load();
  }

  load(){
    this.defaultService.get('tipo-conta').subscribe({
      next: res =>{ this.tiposConta = res; },
      error: error => { this.messageService.add({ severity: 'error', summary: 'Error', detail: 'consulta de tipo de contas' }); },
      complete: () => {}
    });
  }

  rowStyle(ativo: boolean) {
    return !ativo ? {textDecoration: 'line-through', color:'red'} : {textDecoration: 'none'} ;
  }

  onRowEditInit(tipo: any) {
    this.clonedTipos[tipo.id as string] = { ...tipo };
  }

  onRowEditCancel(tipo: any, index: number) {
    this.tipos[index] = this.clonedTipos[tipo.id as string];
    delete this.clonedTipos[tipo.id as string];
  }

  onRowEditSave(tipo: any) {
    delete this.clonedTipos[tipo.id as string];
    this.save(tipo);
  }

  save(tipo: any) {
    if (tipo.nome.trim() !== '') {
      this.loading=true;
      this.defaultService.update(tipo,'tipo-conta').subscribe({
        next: res =>{ this.load() },
        error: error => { this.messageService.add({ severity: 'error', summary: 'Error', detail: 'consulta de tipo de contas' }); },
        complete: () => {
          this.loading=false;
        }
      });
    } else {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Nome inválido!' });
    }

  }
}
