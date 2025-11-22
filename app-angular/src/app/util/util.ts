import {MessageService} from 'primeng/api';
import { TipoMessage } from '@util/tipo-message';
import { TitleMessage } from '@util/title-message';

export class Util {
  static capitalizeSentence(sentence: string): string {
    return sentence
      .split(' ')  // Divide a frase em palavras
      .map(word =>
        word.charAt(0).toUpperCase() + word.slice(1).toLowerCase()  // Capitaliza a primeira letra e mantém as outras minúsculas
      )
      .join(' ');  // Junta as palavras de volta em uma frase
  }

  static dataStringBrToDateString(dataBr:string):string{
    if(dataBr){
      const partes = dataBr.split('/');
      const dia = partes[0];
      const mes = partes[1];
      const ano = partes[2];
      return `${ano}-${mes}-${dia}`;
    }
    return '';
  }

  static formatFloatToReal(valor: string): string{
    var v = valor.replace(/\D/g, '');
    v = (Number(v) / 100).toFixed(2) + '';
    v = v.replace('.', ',');
    v = v.replace(/(\d)(\d{3})(\d{3}),/g, '$1.$2.$3,');
    v = v.replace(/(\d)(\d{3}),/g, '$1.$2,');
    return v.toString();
  }

  static formatMoedaToFloat(valor: string): number{
    return Number(valor.replace('.', '').replace(',', '.'));
  }

  static dataBRtoDataIso(data: String){
    const dataarr  = data.toString().split('/');
    return dataarr[2] + '-' + dataarr[1] + '-' + dataarr[0]
  }

  static dateToDataBR(data: String){
    const dataarr  = data.toString().split('-');
    return dataarr[2] + '/' + dataarr[1] + '/' + dataarr[0]
  }

  // Validador customizado para data no formato dd/mm/aaaa
  static dataIsValida(data:string){
    const regex = /^(0[1-9]|[12][0-9]|3[01])\/(0[1-9]|1[0-2])\/\d{4}$/;
    return ['', null, undefined].indexOf(data) != 0 && regex.test(data);
  }

  static maskaraTeste(valor: string): string{
    // Remove tudo que não for dígito
    valor = valor.replace(/\D/g, "");
    // Limita a 5 dígitos
    valor = valor.substring(0, 5);
    // Adiciona a barra após o segundo dígito
    if (valor.length > 2) {
      valor = valor.substring(0, 2) + "/" + valor.substring(2);
    }
    return valor;
  }

  static showMsgErro(messageService: MessageService, msg:string){
    messageService.add({severity: TipoMessage.ERROR, summary: TitleMessage.ERROR, detail: msg});
  }
  static showMsgSuccess(messageService: MessageService, msg:string){
    messageService.add({severity: TipoMessage.SUCCCESS, summary: TitleMessage.SUCCCESS, detail: msg});
  }
  static showMsgInfo(messageService: MessageService, msg:string){
    messageService.add({severity: TipoMessage.INFO, summary: TitleMessage.INFO, detail: msg});
  }

}
