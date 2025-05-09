import {Component, EventEmitter, Input, Output} from '@angular/core';
import {InputMask} from "primeng/inputmask";
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-input-date',
  imports: [
    InputMask,
    FormsModule
  ],
  templateUrl: './input-date.component.html',
  styleUrl: './input-date.component.css'
})
export class InputDateComponent {
  @Input() valor='';
  @Input() larg='80px';
  @Input() shClear:boolean=false;
  @Output() valorChange = new EventEmitter<string>();

  aoDigitar(event: Event) {
    const input = event.target as HTMLInputElement;
    this.valorChange.emit(input.value);
  }
}
