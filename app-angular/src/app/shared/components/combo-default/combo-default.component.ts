import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Select} from 'primeng/select';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-combo-default',
  imports: [
    Select,
    FormsModule
  ],
  templateUrl: './combo-default.component.html',
  styleUrl: './combo-default.component.css'
})
export class ComboDefaultComponent {
  @Input() larg:string='300px';
  @Input() scrlHeight:string='400px';
  @Input() values:any[]=[];
  @Input() loading:any;
  @Input() optLabel='nome';
  @Input() filtBy='nome';
  @Input() selected:any;
  @Input() shClear:boolean=false;
  @Output() selectedChange = new EventEmitter<any>();

  aoSelecionar(event: any) {
    const input = event.value as HTMLInputElement;
    this.selectedChange.emit(input);
  }
}
