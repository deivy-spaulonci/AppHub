import {Component, Input} from '@angular/core';
import {ProgressSpinner} from 'primeng/progressspinner';
import {BlockUI} from 'primeng/blockui';
import {PrimeTemplate} from 'primeng/api';

@Component({
  selector: 'app-loading-modal',
  imports: [
    ProgressSpinner,
    BlockUI,
    PrimeTemplate
  ],
  templateUrl: './loading-modal.component.html',
  styleUrl: './loading-modal.component.css'
})
export class LoadingModalComponent {
  @Input() visible: boolean = false; // controla a visibilidade
  @Input() message: string = 'Carregando...'; // mensagem opcional

}
