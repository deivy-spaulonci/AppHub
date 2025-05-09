import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Card} from 'primeng/card';
import {Toast} from 'primeng/toast';
import {MessageService} from 'primeng/api';
import {ActivatedRoute, Router} from '@angular/router';
import {DefaultService} from '../../../service/default.service';
import {InputMask} from 'primeng/inputmask';
import {FormsModule} from '@angular/forms';
import {TipoConta} from '../../../model/tipo-conta';
import {Select} from 'primeng/select';
import {InputNumber} from 'primeng/inputnumber';
import {Divider} from 'primeng/divider';
import {InputText} from 'primeng/inputtext';
import {Util} from '../../../util/util';
import {FormaPagamento} from '../../../model/forma-pagamento';
import {Button} from 'primeng/button';
import {CurrencyPipe, JsonPipe, NgIf} from '@angular/common';
import {Panel} from 'primeng/panel';
import {Dialog} from 'primeng/dialog';
import {Fornecedor} from '../../../model/fornecedor';
import {Fatura} from '../../../model/fatura';
import {TableModule} from 'primeng/table';
import {Tooltip} from 'primeng/tooltip';
import {Conta} from '../../../model/conta';
import {Tab, TabList, TabPanel, TabPanels, Tabs} from 'primeng/tabs';
import {firstValueFrom} from 'rxjs';
import {Message} from 'primeng/message';
import {Textarea} from 'primeng/textarea';
import {FileUpload, UploadEvent} from 'primeng/fileupload';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';
import {FaturaTableComponent} from '../../../shared/components/fatura-table/fatura-table.component';
import {InputDateComponent} from '../../../shared/components/input-date/input-date.component';
import {InputParcelasComponent} from '../../../shared/components/input-parcelas/input-parcelas.component';
import {InputMoneyComponent} from '../../../shared/components/input-money/input-money.component';
import {ComboDefaultComponent} from '../../../shared/components/combo-default/combo-default.component';
import {Toolbar} from 'primeng/toolbar';

@Component({
  selector: 'app-conta-form',
  imports: [
    Card,
    Toast,
    InputMask,
    FormsModule,
    Select,
    InputNumber,
    Divider,
    InputText,
    Button,
    NgIf,
    Panel,
    Dialog,
    TableModule,
    CurrencyPipe,
    Tooltip,
    JsonPipe,
    Tabs,
    TabList,
    Tab,
    TabPanels,
    TabPanel,
    Message,
    Textarea,
    FileUpload,
    FaturaTableComponent,
    InputDateComponent,
    InputParcelasComponent,
    InputMoneyComponent,
    ComboDefaultComponent,
    Toolbar
  ],
  templateUrl: './conta-form.component.html',
  standalone: true,
  styleUrl: './conta-form.component.css',
  providers: [MessageService],
})
export class ContaFormComponent  implements OnInit{
  tiposContas:TipoConta[]=[];
  formasPagamento:FormaPagamento[]=[];
  fornecedores:Fornecedor[]=[];
  faturas:Fatura[]=[];
  loading:boolean=false;
  codBarras:string='';
  tipoContaSelecionado:TipoConta = new TipoConta();
  formaPgtoSelecionado:FormaPagamento = new FormaPagamento();
  emissao:string='';
  vencimento:string='';
  dataPagto:string='';
  parcela:number=0;
  totalParcela:number=0;
  valor:string='0,00';
  obs:string='';
  idEdicao!:number;
  fatura:Fatura=new Fatura();
  faturaValor:string='0,00';
  valorTotal:number=0;
  maskCnpj:string='999999999999999999999999999999999999999999999999';

  conteudoBase64: SafeResourceUrl | null = null;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private defaultService: DefaultService,
              private messageService: MessageService,
              private sanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.idEdicao = params['id'];
    });

    this.loadInit();
    this.getTotal();

    if(this.idEdicao){
      this.defaultService.get('conta/'+this.idEdicao).subscribe({
        next: res => {
          this.codBarras = res.codigoBarra;
          this.tipoContaSelecionado = res.tipoConta;
          this.formaPgtoSelecionado= res.formaPagamento;
          this.emissao = Util.dateToDataBR(res.emissao);
          this.vencimento = Util.dateToDataBR(res.vencimento);
          this.parcela = res.parcela;
          this.totalParcela = res.totalParcela;
          this.valor = Util.formatFloatToReal(res.valor.toFixed(2).toString());
          this.obs = res.obs
          this.dataPagto = Util.dateToDataBR(res.dataPagamento);
          this.formaPgtoSelecionado = res.formaPagamento;
          this.faturas = res.faturas;
        },
        error: error => {},
        complete: () => {}
      });
    }
  }

  goToContalist(){
    this.router.navigate(['/conta'])
  }

  aoSelecionar(event: any) {
    const file: File = event.files[0];
    const reader = new FileReader();

    reader.onload = () => {
      const resultado = reader.result as string; // já é um data URL
      this.conteudoBase64 = this.sanitizer.bypassSecurityTrustResourceUrl(resultado);
    };

    reader.readAsDataURL(file);
  }

  async loadInit(){
    this.tiposContas = await firstValueFrom(this.defaultService.get('tipo-conta?sort=nome,asc'));
    this.formasPagamento = await firstValueFrom(this.defaultService.get('forma-pagamento'));
    this.fornecedores = await firstValueFrom(this.defaultService.get('fornecedor'));
    if(!this.idEdicao){
      this.tipoContaSelecionado = this.tiposContas[0];
      this.fatura.fornecedor = this.fornecedores[0];
    }
  }

  async getTotal(){
    this.valorTotal = await firstValueFrom(this.defaultService.get('conta/valorTotal'))
  }

  addFatura(){
    if(!Util.dataIsValida(this.fatura.dataPagamento))
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Data da fatura inválida!'});
    else if(['0,00','0','', null, undefined].indexOf(this.faturaValor.trim()) == 0)
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Valor data fatura inválida!'});
    else{
      var fatura = Object.assign({}, this.fatura);
      fatura.valor  = Util.formatMoedaToFloat(Util.formatFloatToReal(this.faturaValor));
      fatura.parcela = Number(fatura.parcela) ? Number(fatura.parcela)  : 0;
      fatura.totalParcela = Number(fatura.totalParcela) ? Number(fatura.totalParcela) : 0;
      fatura.dataPagamento = Util.dataBRtoDataIso(fatura.dataPagamento);
      this.faturas.push(fatura);
      this.faturaValor = '0';
      this.fatura.parcela = 0;
      this.fatura.totalParcela = 0;
    }
  }

  save(){
    this.parcela = Number(this.parcela);
    this.totalParcela = Number(this.totalParcela);
    if(this.codBarras.trim()=="")
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Código de Barras inválido!'});
    else if(!Util.dataIsValida(this.emissao))
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Emissão inválida!'});
    else if(!Util.dataIsValida(this.vencimento))
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Vencimento inválida!'});
    else if(this.parcela > this.totalParcela)
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Parcela inválida!'});
    else if(['0,00','0','', null, undefined].indexOf(this.valor.trim()) == 0)
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Valor inválido!'});
    else if(this.formaPgtoSelecionado && !Util.dataIsValida(this.emissao))
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Data Pagamento inválida!'});
    else if(!this.formaPgtoSelecionado && Util.dataIsValida(this.emissao))
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Forma de Pagamento inválida!'});
    else if(!this.tipoContaSelecionado.cartaoCredito && this.faturas.length > 0)
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Conta com faturas (nao e conta cartao)!'});
    else if(Util.dataBRtoDataIso(this.emissao) > Util.dataBRtoDataIso(this.vencimento))
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'emissão maior que o vencimento'});
    else{

      this.loading=true;
      var conta:Conta = new Conta();
      if(this.idEdicao)
         conta.id = this.idEdicao;

      conta.codigoBarra = this.codBarras;
      conta.tipoConta = this.tipoContaSelecionado;
      conta.emissao = Util.dataBRtoDataIso(this.emissao);
      conta.vencimento = Util.dataBRtoDataIso(this.vencimento);

      conta.parcela = this.parcela;
      conta.totalParcela = this.totalParcela;
      conta.valor =  Util.formatMoedaToFloat(Util.formatFloatToReal(this.valor));
      conta.obs = this.obs;
      if(this.formaPgtoSelecionado && Util.dataIsValida(this.dataPagto)){
        conta.formaPagamento = this.formaPgtoSelecionado;
        conta.dataPagamento = Util.dataBRtoDataIso(this.dataPagto);
      }else{
        conta.formaPagamento = undefined;
        conta.dataPagamento = '';
      }
      conta.faturas = this.faturas;

      this.defaultService.save(conta, 'conta').subscribe({
        next: res => {
          this.messageService.add({severity: 'success', summary: 'Success', detail: 'Conta salva!'});

          conta = new Conta();
        },
        error: error => {
          this.messageService.add({severity: 'error', summary: 'Error', detail: 'erro ao salvar o conta'});
        },
        complete: () => {
          if(this.idEdicao)
            this.router.navigate(['/conta'])
          this.loading = false;
          this.valor = '0,00';
          this.obs = '';
        }
      });
    }
  }

}
