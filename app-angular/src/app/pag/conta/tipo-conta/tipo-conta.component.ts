import {Component, OnInit} from '@angular/core';
import {TableModule} from 'primeng/table';
import {ConfirmationService, MessageService} from 'primeng/api';
import {TipoConta} from '@model/tipo-conta';
import {NgIf, NgStyle} from '@angular/common';
import {Card} from 'primeng/card';
import {Button} from 'primeng/button';
import {ConfirmDialog} from 'primeng/confirmdialog';
import {Tooltip} from 'primeng/tooltip';
import {Toast} from 'primeng/toast';
import {LoadingModalComponent} from '@shared/loading-modal/loading-modal.component';
import {InputText} from 'primeng/inputtext';
import {FormsModule} from '@angular/forms';
import {Checkbox} from 'primeng/checkbox';
import {TipoContaService} from '@service/TipoContaService';
import {Util} from '@util/util';

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
    Checkbox,
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

  constructor(private tipoContaService: TipoContaService,
              private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.load();
  }

  load(){
    this.tipoContaService.getTiposConta().subscribe({
      next: (res: TipoConta[]) => this.tiposConta = res,
      error: () => Util.showMsgErro(this.messageService,'consulta de tipo de contas'),
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
      this.tipoContaService.update(tipo).subscribe({
        next: () =>this.load(),
        error: () => Util.showMsgErro(this.messageService, 'erro na consulta de tipo de contas'),
        complete: () => this.loading=false
      });
    } else {
      Util.showMsgErro(this.messageService,'Nome inválido!');
    }
  }
}
