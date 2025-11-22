import {Component, OnInit} from '@angular/core';
import {TableLazyLoadEvent, TableModule} from 'primeng/table';
import {CardModule} from 'primeng/card';
import {Fornecedor} from '@model/fornecedor';
import {ButtonModule} from 'primeng/button';
import {InputGroupModule} from 'primeng/inputgroup';
import {FormsModule} from '@angular/forms';
import {InputTextModule} from 'primeng/inputtext';
import {InputGroupAddonModule} from 'primeng/inputgroupaddon';
import {ToastModule} from 'primeng/toast';
import {MessageService} from 'primeng/api';
import {NgIf} from '@angular/common';
import {Tooltip} from 'primeng/tooltip';
import {Util} from '@util/util';
import {Message} from "primeng/message";
import {Toolbar} from 'primeng/toolbar';
import {LoadingModalComponent} from '@shared/loading-modal/loading-modal.component';
import {FornecedorService} from '@service/FornecedorService';

@Component({
  selector: 'app-fornecedor-table',
  imports: [ToastModule, TableModule, CardModule, ButtonModule, InputGroupModule, FormsModule, InputTextModule, InputGroupAddonModule, NgIf, Tooltip, Message, Toolbar, LoadingModalComponent],
  templateUrl: './fornecedor-table.component.html',
  styleUrl: './fornecedor-table.component.css',
  providers: [MessageService],
  standalone: true,
})
export class FornecedorTableComponent implements OnInit {
  fornecedores: Fornecedor[] = [];
  filtroTexto: string = '';
  totalElements = 0;
  sortField: string = 'nome';
  pageSize = 10;
  loading: boolean = false;
  fornecedorSelecionado!: Fornecedor;
  clonedFornecedors: { [s: number]: Fornecedor } = {};

  constructor(private fornecedorService: FornecedorService,
              private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.loading = true;
    this.fornecedorService.getFornecedoresPage('').subscribe({
      next: res => this.fornecedores = res.content,
      error: err => Util.showMsgErro(this.messageService,'consulta de fornecedores'),
      complete: () => this.loading = false
    });
  }

  addMsg(tipo:string, summary:string, msg:string){
    this.messageService.add({severity: tipo, summary: summary, detail: msg});
  }

  onRowEditInit(fornecedor: Fornecedor) {
    this.clonedFornecedors[fornecedor.id as number] = {...fornecedor};
  }

  updateNameFonnecedor(fornecedor: Fornecedor) {
    fornecedor.nome = Util.capitalizeSentence(fornecedor.nome);
    fornecedor.razaoSocial = Util.capitalizeSentence(fornecedor.razaoSocial);
    this.updateFornecedor(fornecedor);
  }

  updateFornecedor(fornecedor: Fornecedor) {
    this.loading = true;
    this.fornecedorService.update(fornecedor).subscribe({
      next: res => Util.showMsgSuccess(this.messageService,'Fornecedor atualizado'),
      error: err => Util.showMsgErro(this.messageService,'Erro ao salvar fornecedor'),
      complete: () => {
        delete this.clonedFornecedors[fornecedor.id as number];
        this.loading = false;
      }
    });
  }

  onRowEditSave(fornecedor: Fornecedor) {
    if (fornecedor.nome.trim().length > 0 && fornecedor.razaoSocial.trim().length > 0)
      this.updateFornecedor(fornecedor)
    else
      Util.showMsgErro(this.messageService, 'Nome ou Razão Social inválido(s)');
  }

  onRowEditCancel(fornecedor: Fornecedor, index: number) {
    this.fornecedores[index] = this.clonedFornecedors[fornecedor.id as number];
    delete this.clonedFornecedors[fornecedor.id as number];
  }

  loadData(event: TableLazyLoadEvent) {
    let urlfiltros: string = '';

    this.loading = true;

    if (this.filtroTexto)
      urlfiltros = '&busca=' + this.filtroTexto;

    event.rows = (event.rows ? event.rows : this.pageSize);
    event.sortField = (event.sortField ? event.sortField : 'nome');

    const url: string = '?page=' + (event.first! / this.pageSize)
      + '&size=' + event.rows
      + '&sort=' + event.sortField + ',' + (event.sortOrder == 1 ? 'asc' : 'desc')
      + urlfiltros;

    this.fornecedorService.getFornecedoresPage(url).subscribe({
      next: res => {
        if (res) {
          this.fornecedores = res.content;
          this.totalElements = res.totalElements;
          Util.showMsgInfo(this.messageService, 'fornecedores carregados');
        } else {
          this.fornecedores = [];
          this.totalElements = 0;
        }
      },
      error: err => Util.showMsgErro(this.messageService,'Erro ao carregar fornecedores'),
      complete: () => this.loading = false
    });
  }
}
