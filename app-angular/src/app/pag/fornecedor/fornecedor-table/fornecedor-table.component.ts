import {Component, OnInit} from '@angular/core';
import {DefaultService} from '../../../service/default.service';
import {TableLazyLoadEvent, TableModule} from 'primeng/table';
import {CardModule} from 'primeng/card';
import {Fornecedor} from '../../../model/fornecedor';
import {ButtonModule} from 'primeng/button';
import {InputGroupModule} from 'primeng/inputgroup';
import {FormsModule} from '@angular/forms';
import {InputTextModule} from 'primeng/inputtext';
import {InputGroupAddonModule} from 'primeng/inputgroupaddon';
import {Toast, ToastModule} from 'primeng/toast';
import {MessageService} from 'primeng/api';
import {NgIf} from '@angular/common';
import {Tooltip} from 'primeng/tooltip';
import {Util} from '../../../util/util';
import {Message} from "primeng/message";
import {Panel} from "primeng/panel";
import {Router} from '@angular/router';

@Component({
  selector: 'app-fornecedor-table',
    imports: [Toast, ToastModule, TableModule, CardModule, ButtonModule, InputGroupModule, FormsModule, InputTextModule, InputGroupAddonModule, NgIf, Tooltip, Message, Panel],
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
  pageSize = 20;
  loading: boolean = false;
  fornecedorSelecionado!: Fornecedor;
  clonedFornecedors: { [s: number]: Fornecedor } = {};

  constructor(private router: Router,
              private defaultService: DefaultService,
              private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.defaultService.get('fornecedor/page').subscribe({
      next: res => {
        this.fornecedores = res.content;
      },
      error: error => {
        this.messageService.add({severity: 'error', summary: 'Error', detail: 'consulta de fornecedoress'});
      },
      complete: () => {
      }
    });
  }

  goFornecedorForm(){
    this.router.navigate(['/fornecedor-form'])
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
    this.defaultService.update(fornecedor, 'fornecedor').subscribe({
      next: res => {
        this.messageService.add({severity: 'success', summary: 'Success', detail: 'Fornecedor atualizado'});
      },
      error: error => {
        this.messageService.add({severity: 'error', summary: 'Error', detail: 'salvar fornecedor'});
      },
      complete: () => {
        delete this.clonedFornecedors[fornecedor.id as number];
      }
    });
  }

  onRowEditSave(fornecedor: Fornecedor) {
    if (fornecedor.nome.trim().length > 0 && fornecedor.razaoSocial.trim().length > 0) {
      this.updateFornecedor(fornecedor)
    } else {
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Nome ou Razão Social inválido(s)'});
    }
  }

  onRowEditCancel(fornecedor: Fornecedor, index: number) {
    this.fornecedores[index] = this.clonedFornecedors[fornecedor.id as number];
    delete this.clonedFornecedors[fornecedor.id as number];
  }

  loadData(event: TableLazyLoadEvent) {
    let urlfiltros: string = '';
    // let prefix: string =''
    this.loading = true;

    if (this.filtroTexto)
      urlfiltros = '&busca=' + this.filtroTexto;

    event.rows = (event.rows ? event.rows : this.pageSize);
    event.sortField = (event.sortField ? event.sortField : 'nome');

    const url: string = 'fornecedor/page?page=' + (event.first! / this.pageSize)
      + '&size=' + event.rows
      + '&sort=' + event.sortField + ',' + (event.sortOrder == 1 ? 'asc' : 'desc')
      + urlfiltros;
    console.log(url);
    this.defaultService.get(url).subscribe({
      next: resultado => {
        this.fornecedores = resultado.content;
        this.totalElements = resultado.totalElements;
        this.messageService.add({
          severity: 'info',
          summary: 'Info',
          detail: `${this.totalElements} fornecedores carregados`,
          life: 3000
        });
      },
      error: error => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Erro ao carregar fornecedores',
          life: 3000
        });
      },
      complete: () => {
        this.loading = false;
      }
    });
  }
}
