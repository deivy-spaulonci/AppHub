
import {TipoConta} from "./tipo-conta";
import {FormaPagamento} from './forma-pagamento';

export class Conta {
  id!: number;
  tipoConta!: TipoConta;
  codigoBarra!: string;
  emissao!: string;
  vencimento!:  string;
  valor!: number;
  parcela!: number;
  totalParcela!: number;
  formaPagamento?: FormaPagamento;
  dataPagamento?: string;
  multa?: number;
  desconto?: number;
  valorPago!: number;
  titulo!: string;
  comprovante!: string;
  status!: string;
  intStatus!: number;
  obs!: string;

}
