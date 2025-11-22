import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DefaultService } from './default.service';
import { FormaPagamento } from '@model/forma-pagamento';

@Injectable({
  providedIn: 'root'
})
export class FormaPagamentoService {

  ROOT: string = "forma-pagamento";

  constructor(private defaultService: DefaultService) {}

  getFormasPagamento(): Observable<FormaPagamento[]> {
    return this.defaultService.get(this.ROOT);
  }
}
