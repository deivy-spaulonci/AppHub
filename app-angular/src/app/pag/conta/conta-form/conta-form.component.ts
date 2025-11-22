import {Component, OnInit} from '@angular/core';
import {Toast} from 'primeng/toast';
import {MessageService} from 'primeng/api';
import {ActivatedRoute, Router} from '@angular/router';
import {InputMask} from 'primeng/inputmask';
import {FormsModule} from '@angular/forms';
import {TipoConta} from '@model/tipo-conta';
import {Divider} from 'primeng/divider';
import {Util} from '@util/util';
import {FormaPagamento} from '@model/forma-pagamento';
import {Button} from 'primeng/button';
import {NgIf} from '@angular/common';
import {Fornecedor} from '@model/fornecedor';
import {TableModule} from 'primeng/table';
import {firstValueFrom} from 'rxjs';
import {Textarea} from 'primeng/textarea';
import {FileUpload} from 'primeng/fileupload';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';
import {InputDateComponent} from '@shared/components/input-date/input-date.component';
import {InputParcelasComponent} from '@shared/components/input-parcelas/input-parcelas.component';
import {InputMoneyComponent} from '@shared/components/input-money/input-money.component';
import {ComboDefaultComponent} from '@shared/components/combo-default/combo-default.component';
import {Toolbar} from 'primeng/toolbar';
import {Conta} from '@model/conta';
import {LoadingModalComponent} from '@shared/loading-modal/loading-modal.component';
import {ContaService} from '@service/ContaService';
import {TipoContaService} from '@service/TipoContaService';
import {FormaPagamentoService} from '@service/FormaPagamentoService';
import {FornecedorService} from '@service/FornecedorService';

@Component({
  selector: 'app-conta-form',
  imports: [
    Toast,
    InputMask,
    FormsModule,
    Divider,
    Button,
    NgIf,
    TableModule,
    Textarea,
    FileUpload,
    InputDateComponent,
    InputParcelasComponent,
    InputMoneyComponent,
    ComboDefaultComponent,
    Toolbar,
    LoadingModalComponent
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
  valorTotal:number=0;
  maskCnpj:string='999999999999999999999999999999999999999999999999';
  valorPago:string='0,00';

  conteudoBase64: SafeResourceUrl | null = null;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private contaService: ContaService,
              private tipoContaService: TipoContaService,
              private formaPagamentoService: FormaPagamentoService,
              private fornecedorService: FornecedorService,
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
      this.contaService.getContasById(this.idEdicao).subscribe({
        next: (res:Conta) => {
          this.codBarras = res.codigoBarra;
          this.tipoContaSelecionado = res.tipoConta;
          this.formaPgtoSelecionado = res.formaPagamento ? res.formaPagamento : new FormaPagamento();
          this.emissao = res.emissao ? Util.dateToDataBR(res.emissao) : '';
          this.vencimento = Util.dateToDataBR(res.vencimento);
          this.parcela = res.parcela;
          this.totalParcela = res.totalParcela;
          this.valor = Util.formatFloatToReal(res.valor.toFixed(2).toString());
          this.valorPago = Util.formatFloatToReal(res.valorPago.toFixed(2).toString());
          this.obs = res.obs
          this.dataPagto = res.dataPagamento ? Util.dateToDataBR(res.dataPagamento) : '';
          this.formaPgtoSelecionado = res.formaPagamento ? res.formaPagamento : new FormaPagamento();
        },
        error: () => Util.showMsgErro(this.messageService, 'Erro ao consulta conta'),
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
    this.tiposContas = await firstValueFrom(this.tipoContaService.getTiposConta());
    this.formasPagamento = await firstValueFrom(this.formaPagamentoService.getFormasPagamento());
    this.fornecedores = await firstValueFrom(this.fornecedorService.getFornecedores());
    if(!this.idEdicao){
      this.tipoContaSelecionado = this.tiposContas[0];
    }
  }

  async getTotal(){
    this.valorTotal = await firstValueFrom(this.contaService.getValorTotal(''));
  }

  save(){
    this.parcela = Number(this.parcela);
    this.totalParcela = Number(this.totalParcela);

    if(this.codBarras.trim()=="")
      Util.showMsgErro(this.messageService, 'Código de Barras inválido!');
    else if(!Util.dataIsValida(this.emissao))
      Util.showMsgErro(this.messageService, 'Emissão inválida!');
    else if(!Util.dataIsValida(this.vencimento))
      Util.showMsgErro(this.messageService, 'Vencimento inválida!');
    else if(this.parcela > this.totalParcela)
      Util.showMsgErro(this.messageService, 'Parcela inválida!');
    else if(['0,00','0','', null, undefined].indexOf(this.valor.trim()) == 0)
      Util.showMsgErro(this.messageService, 'Valor inválido!');
    else if(this.dataPagto && !Util.dataIsValida(this.dataPagto))
      Util.showMsgErro(this.messageService, 'Data Pagamento inválida!');
    else if(!this.formaPgtoSelecionado.id && Util.dataIsValida(this.dataPagto))
      Util.showMsgErro(this.messageService, 'Forma de Pagamento inválida!');
    else if(Util.dataBRtoDataIso(this.emissao) > Util.dataBRtoDataIso(this.vencimento))
      Util.showMsgErro(this.messageService, 'emissão maior que o vencimento');
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
      conta.multa = 0;
      conta.desconto = 0;
      if(this.formaPgtoSelecionado && Util.dataIsValida(this.dataPagto)){
        conta.formaPagamento = this.formaPgtoSelecionado;
        conta.dataPagamento = Util.dataBRtoDataIso(this.dataPagto);

        var vlPg:number = Util.formatMoedaToFloat(Util.formatFloatToReal(this.valorPago));
        if(vlPg > conta.valor)
            conta.multa = Math.round((vlPg - conta.valor)*100)/100;
        if(vlPg < conta.valor)
            conta.desconto = Math.round((conta.valor - vlPg)*100)/100;
      }else{
        conta.formaPagamento = undefined;
        conta.dataPagamento = '';
      }

      if(conta.id)
        this.updateConta(conta);
      else
        this.createConta(conta);
    }
  }

  createConta(conta:Conta){
    this.contaService.create(conta).subscribe({
      next: () => {
        Util.showMsgSuccess(this.messageService, 'Conta salva!')
        this.codBarras = '';
        conta = new Conta();
      },
      error: () => Util.showMsgErro(this.messageService, 'erro ao salvar o conta'),
      complete: () => {
        this.loading = false;
        this.valor = '0,00';
        this.obs = '';
      }
    });
  }

  updateConta(conta:Conta){
    this.contaService.update(conta).subscribe({
      next: () => {
        Util.showMsgSuccess(this.messageService, 'Conta atualizada!')
        this.codBarras = '';
        conta = new Conta();
      },
      error: () => Util.showMsgErro(this.messageService, 'erro ao salvar o conta'),
      complete: () => {
        this.loading = false;
        this.router.navigate(['/conta'])
        this.valor = '0,00';
        this.obs = '';
      }
    });
  }
}
