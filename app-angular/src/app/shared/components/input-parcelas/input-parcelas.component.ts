import {Component, EventEmitter, Input, Output} from '@angular/core';
import {InputMask} from 'primeng/inputmask';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-input-parcelas',
  imports: [
    InputMask,
    FormsModule
  ],
  templateUrl: './input-parcelas.component.html',
  styleUrl: './input-parcelas.component.css'
})
export class InputParcelasComponent {
  @Input() desabilitado:boolean=false;
  @Input() valor=0;
  @Input() total=0;

  @Output() valorChange = new EventEmitter<string>();
  @Output() totalChange = new EventEmitter<string>();

  aoDigitarValor(event: Event) {
    const input = event.target as HTMLInputElement;
    this.valorChange.emit(input.value);
  }
  aoDigitarTotal(event: Event) {
    const input = event.target as HTMLInputElement;
    this.totalChange.emit(input.value);
  }
}
