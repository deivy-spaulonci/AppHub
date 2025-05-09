import {ChangeDetectorRef, Component, effect, inject, OnInit, PLATFORM_ID} from '@angular/core';
import {CardModule} from 'primeng/card';
import {MessageModule} from 'primeng/message';
import {DefaultService} from '../../service/default.service';
import {FormsModule} from '@angular/forms';
import {CurrencyPipe, isPlatformBrowser} from '@angular/common';
import {Fieldset} from 'primeng/fieldset';
import {ChartModule, UIChart} from 'primeng/chart';
import {forkJoin} from 'rxjs';

@Component({
  selector: 'app-home',
  imports: [CardModule, MessageModule, FormsModule, CurrencyPipe, Fieldset, UIChart, ChartModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  standalone: true,
  providers: [CurrencyPipe]
})
export class HomeComponent implements OnInit{

  totalDespesa$!: number;
  totalConta$!: number;
  totalContaAvencer$!:number;
  totalContaAberto$!:number;
  totalContaHoje$!:number;
  totalContaAtrasado$!:number;


  data: any;

  options: any;

  platformId = inject(PLATFORM_ID);

  // configService = inject(AppConfigService);
  //
  // designerService = inject(DesignerService);

  themeEffect = effect(() => {
    // if (this.configService.transitionComplete()) {
    //   if (this.designerService.preset()) {
    //     this.initChart();
    //   }
    // }
  });

  constructor(private defaultService: DefaultService,
              private currencyPipe: CurrencyPipe,
              private cd: ChangeDetectorRef) {
  }

  ngOnInit(): void {

    this.defaultService.get('despesa/valorTotal').subscribe(res =>{
      this.totalDespesa$ = res;
    });

    this.defaultService.get('conta/valorTotal').subscribe(res =>{
      this.totalConta$ = res;
    });

    forkJoin({
      pago:this.defaultService.get('conta/valorTotal?contaStatus=PAGO'),
      hoje:this.defaultService.get('conta/valorTotal?contaStatus=HOJE'),
      aberto:this.defaultService.get('conta/valorTotal?contaStatus=ABERTO'),
      atrasado:this.defaultService.get('conta/valorTotal?contaStatus=ATRASADO'),
    }).subscribe({
      next: ({pago,hoje,aberto,atrasado}) => {
        this.totalContaAvencer$ = pago;
        this.totalContaHoje$ = hoje;
        this.totalContaAberto$ = aberto;
        this.totalContaAtrasado$ = atrasado;
        this.initChart();
      },
      error: erro => {
        console.error('Falhou alguma chamada', erro);
      }
    });

  }

  getStatus(status:string){
    this.defaultService.get('conta/valorTotal?contaStatus='+status).subscribe(res =>{
      this.totalContaAvencer$ = res;
    });
  }

  getMoneyFormat(valor:number){
    return valor ? this.currencyPipe.transform(valor, 'BRL', 'symbol', '1.2-2') : '0,00';
  }
  getPercentFormat(valor:number){
    return (valor ? ((valor/this.totalConta$)*100).toFixed(2) : '0');
  }

  initChart() {
    if (isPlatformBrowser(this.platformId)) {
      const documentStyle = getComputedStyle(document.documentElement);
      const textColor = documentStyle.getPropertyValue('--text-color');

      this.data = {
        labels: [
          `Contas Pagas - ${this.getMoneyFormat(this.totalContaAvencer$)} [ ${this.getPercentFormat(this.totalContaAvencer$)}% ]`,
          `Vencem Hoje - ${this.getMoneyFormat(this.totalContaHoje$)} [ ${this.getPercentFormat(this.totalContaHoje$)}% ]`,
          `Estão em Aberto - ${this.getMoneyFormat(this.totalContaAberto$)} [ ${this.getPercentFormat(this.totalContaAberto$)}% ]`,
          `Em atraso - ${this.getMoneyFormat(this.totalContaAtrasado$)} [ ${this.getPercentFormat(this.totalContaAtrasado$)}% ]`
        ],
        datasets: [
          {
            data: [
              this.getPercentFormat(this.totalContaAvencer$),
              this.getPercentFormat(this.totalContaHoje$),
              this.getPercentFormat(this.totalContaAberto$),
              this.getPercentFormat(this.totalContaAtrasado$),
            ],
            backgroundColor: [
              documentStyle.getPropertyValue('--p-blue-500'),
              documentStyle.getPropertyValue('--p-yellow-500'),
              documentStyle.getPropertyValue('--p-green-500'),
              documentStyle.getPropertyValue('--p-red-400'),
            ],
            hoverBackgroundColor: [
              documentStyle.getPropertyValue('--p-blue-400'),
              documentStyle.getPropertyValue('--p-yellow-400'),
              documentStyle.getPropertyValue('--p-green-400'),
              documentStyle.getPropertyValue('--p-red-400'),
            ]
          }
        ]
      };

      this.options = {
        plugins: {
          legend: {
            labels: {
              usePointStyle: true,
              color: textColor
            }
          }
        }
      };
      this.cd.markForCheck()
    }

  }

}
