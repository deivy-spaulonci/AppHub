import {Fornecedor} from "./fornecedor";

export class Fatura {
  id!: number;
  parcela!: number;
  totalParcela!: number;
  dataPagamento!: string;
  valor!: number;
  fornecedor!: Fornecedor;
}
