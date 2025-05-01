
import {Fornecedor} from "./fornecedor";
import {TipoDespesa} from './tipo-despesa';
import {FormaPagamento} from './forma-pagamento';

export class Despesa {
  id!: number;
  tipoDespesa!: TipoDespesa;
  fornecedor!: Fornecedor;
  dataPagamento!: String;
  formaPagamento!: FormaPagamento;
  valor!: number;
  obs!: string;
}
