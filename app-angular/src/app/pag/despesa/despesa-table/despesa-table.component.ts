import {Component, OnInit, ViewChild} from '@angular/core';
import {Card} from 'primeng/card';
import {Despesa} from '../../../model/despesa';
import {DefaultService} from '../../../service/default.service';
import {ConfirmationService, MessageService} from 'primeng/api';
import {Table, TableLazyLoadEvent, TableModule} from 'primeng/table';
import {CurrencyPipe, DatePipe, JsonPipe, NgIf, NgStyle} from '@angular/common';
import {InputText} from 'primeng/inputtext';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {InputNumber} from 'primeng/inputnumber';
import {InputMask} from 'primeng/inputmask';
import {Button, ButtonDirective} from 'primeng/button';
import {InputGroupAddon, InputGroupAddonModule} from 'primeng/inputgroupaddon';
import {InputGroupModule} from 'primeng/inputgroup';
import {IconField} from 'primeng/iconfield';
import {Toast} from 'primeng/toast';
import {Drawer} from 'primeng/drawer';
import {Toolbar} from 'primeng/toolbar';
import {TipoDespesa} from '../../../model/tipo-despesa';
import {FormaPagamento} from '../../../model/forma-pagamento';
import {Select} from 'primeng/select';
import {Fornecedor} from '../../../model/fornecedor';
import {Util} from '../../../util/util';
import {Tooltip} from 'primeng/tooltip';
import {Router, RouterLink} from '@angular/router';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {Message} from 'primeng/message';
import {TipoConta} from '../../../model/tipo-conta';
import {Panel} from "primeng/panel";
import {ComboDefaultComponent} from '../../../shared/components/combo-default/combo-default.component';
import {InputDateComponent} from '../../../shared/components/input-date/input-date.component';
import {LoadingModalComponent} from '../../../shared/loading-modal/loading-modal.component';

@Component({
  selector: 'app-despesa-table',
  imports: [
    Card,
    TableModule,
    DatePipe,
    CurrencyPipe,
    ReactiveFormsModule,
    FormsModule,
    Button,
    InputGroupModule,
    InputGroupAddonModule,
    Toast,
    Tooltip,
    ConfirmDialogModule,
    Message,
    ComboDefaultComponent,
    InputDateComponent,
    LoadingModalComponent
  ],
  templateUrl: './despesa-table.component.html',
  providers: [MessageService,ConfirmationService],
  standalone: true,
  styleUrl: './despesa-table.component.css'
})
export class DespesaTableComponent implements OnInit{
  despesas:Despesa[]=[];
  totalElements = 0;
  sortField:string='dataPagamento';
  pageSize = 10;
  valorTotal:number=0;
  loading:boolean=false;
  despesaSelecinada!:Despesa;
  idFilter:string='';
  tipoDespesaFilter:TipoDespesa=new TipoDespesa;
  formaPgtoFilter:FormaPagamento=new FormaPagamento;
  fornecedorFilter:Fornecedor=new Fornecedor;
  dataInicialFilter:string='';
  dataFinalFilter:string='';
  tiposDespesa:TipoDespesa[]=[];
  formasPgto:FormaPagamento[]=[];
  fornecedores:Fornecedor[]=[];
  @ViewChild('dt') table?:Table;

  constructor(private confirmationService: ConfirmationService,
              private router: Router,
              private defaultService: DefaultService,
              private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.defaultService.get('despesa/page').subscribe({
      next: res =>{ this.despesas = res.content; },
      error: error => { this.messageService.add({ severity: 'error', summary: 'Error', detail: 'consulta de fornecedores' }); },
      complete: () => {}
    });
    this.defaultService.get('tipo-despesa').subscribe({
      next: res =>{ this.tiposDespesa = res; },
      error: error => { this.messageService.add({ severity: 'error', summary: 'Error', detail: 'consulta de tipo despesas' }); },
      complete: () => {}
    });
    this.defaultService.get('forma-pagamento').subscribe({
      next: res =>{ this.formasPgto = res; },
      error: error => { this.messageService.add({ severity: 'error', summary: 'Error', detail: 'consulta de formas de pagamento' }); },
      complete: () => {}
    });
    this.defaultService.get('fornecedor ').subscribe({
      next: res =>{ this.fornecedores = res; },
      error: error => { this.messageService.add({ severity: 'error', summary: 'Error', detail: 'consulta fornecedores' }); },
      complete: () => {}
    })
  }

  editDespesa(param:number){
    this.router.navigate(['/despesa-form'],{ queryParams: { id: param} })
  }

  delDespesa(event: Event, id:number){
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      rejectButtonProps: {label: 'Cancelar',severity: 'secondary',outlined: true,},
      acceptButtonProps: {label: 'Excluir',severity: 'warn',},
      accept: () => {
        this.loading=true;
        this.defaultService.delete(id,'despesa').subscribe({
          next: res =>{
            this.messageService.add({severity: 'success', summary: 'Success', detail: 'Despesa excluída!'});
            this.table?._filter();
          },
          error: error => this.messageService.add({ severity: 'error', summary: 'Error', detail: 'exlcuir despesa' }),
          complete: () => this.loading=true
        })
      },
      reject: () => {},
    });
  }

  clearFilter(){
    this.idFilter='';
    this.tipoDespesaFilter=new TipoDespesa();
    this.fornecedorFilter=new Fornecedor();
    this.formaPgtoFilter=new FormaPagamento();
    this.dataInicialFilter='';
    this.dataFinalFilter='';
    this.table?._filter();
  }

  loadData(event: TableLazyLoadEvent) {
    let urlfiltros: string = '';
    // let prefix: string =''
    this.loading = true;

    if(this.idFilter)
      urlfiltros += '&id='+this.idFilter;
    if(this.tipoDespesaFilter && this.tipoDespesaFilter.id)
      urlfiltros += '&tipoDespesa.id='+ this.tipoDespesaFilter.id;
    if(this.fornecedorFilter && this.fornecedorFilter.id)
      urlfiltros += '&fornecedor.id='+ this.fornecedorFilter.id;
    if(this.formaPgtoFilter && this.formaPgtoFilter.id)
      urlfiltros += '&formaPagamento.id='+this.formaPgtoFilter.id
    if(this.dataInicialFilter)
      urlfiltros += '&dataInicial='+Util.dataStringBrToDateString(this.dataInicialFilter);
    if(this.dataFinalFilter)
      urlfiltros += '&dataFinal='+Util.dataStringBrToDateString(this.dataFinalFilter);

    event.rows = (event.rows ? event.rows : this.pageSize);
    event.sortField = (event.sortField ? event.sortField : 'dataPagamento');

    const url: string = 'despesa/page?page=' + (event.first! / event.rows)
      + '&size=' + event.rows
      + '&sort=' + event.sortField + ',' + (event.sortOrder == 1 ? 'asc' : 'desc')
      + urlfiltros;

    this.defaultService.get(url).subscribe({
      next: resultado => {
        this.despesas = [];
        this.totalElements = 0;
        if(resultado) {
          this.despesas = resultado.content;
          this.totalElements = resultado.totalElements;
          this.defaultService.get('despesa/valorTotal?'+urlfiltros).subscribe({
            next: resultado => this.valorTotal = resultado
          })
        }
        this.messageService.add({severity: 'info',summary: 'Info',detail: `${this.totalElements} despesas carregadas`,life: 3000});
      },
      error: error => this.messageService.add({severity: 'error',summary: 'Error',detail: 'Erro ao carregar as despesas',life: 3000}),
      complete: () => this.loading = false
    });
  }
}
