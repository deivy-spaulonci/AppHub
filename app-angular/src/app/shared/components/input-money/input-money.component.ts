import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Util} from '@util/util';
import {InputText} from 'primeng/inputtext';

@Component({
  selector: 'app-input-money',
  imports: [
    FormsModule,
    InputText
  ],
  templateUrl: './input-money.component.html',
  styleUrl: './input-money.component.css'
})
export class InputMoneyComponent {
  @Input() desabilitado:boolean=false;
  @Input() money:string='0,00';
  @Input() larg:string='80px'
  @Output() moneyChange = new EventEmitter<string>();

  maskaraMoeda($event: KeyboardEvent) {
    const element = ($event.target as HTMLInputElement);
    element.value = Util.formatFloatToReal(element.value);
    this.moneyChange.emit(element.value);
  }
}
