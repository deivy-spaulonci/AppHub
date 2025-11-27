import {Component, OnInit} from '@angular/core';
import {ConfirmationService, MessageService} from 'primeng/api';
import {Button} from 'primeng/button';
import {Toast} from 'primeng/toast';
import {Toolbar} from 'primeng/toolbar';
import {InputText} from 'primeng/inputtext';
import {TableModule} from 'primeng/table';
import {NgForOf, NgIf} from '@angular/common';
import {DomSanitizer} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {ConfirmDialog} from 'primeng/confirmdialog';
import {Dialog} from 'primeng/dialog';
import {ToggleButton} from 'primeng/togglebutton';
import {Divider} from 'primeng/divider';
import {DocumentoService} from '@service/DocumentoService';
import {Util} from '@util/util';
import {LoadingModalComponent} from '@shared/loading-modal/loading-modal.component';

@Component({
  selector: 'app-documento',
  imports: [
    Button,
    Toast,
    Toolbar,
    InputText,
    TableModule,
    FormsModule,
    NgForOf,
    ConfirmDialog,
    Dialog,
    NgIf,
    ToggleButton,
    Divider,
    LoadingModalComponent,
  ],
  templateUrl: './documento.component.html',
  standalone: true,
  styleUrl: './documento.component.css',
  providers: [ConfirmationService,MessageService],
})
export class DocumentoComponent implements OnInit{
  files!: any[];
  loading: boolean = false;
  selectedFile!: any;
  basePath:string='/media/d31vy/hd01/Pagamentos/Pagamentos_PF/2019/01-2019/';
  popupRenShow= false;
  newNameFile:string='';
  filesSelected:string[]=[];

  constructor(private confirmationService: ConfirmationService,
              private documentoService: DocumentoService,
              private sanitizer: DomSanitizer,
              private messageService: MessageService,) {}

  ngOnInit() {
  }

  carregar(){
    this.loading=true;
    this.documentoService.getDocumentoByPath(this.basePath).subscribe({
      next: (res: any[]) => this.files = res,
      error: () => Util.showMsgErro(this.messageService,'consulta de documentos'),
      complete: () => this.loading=false
    });
  }

  initRename(file:any) {
    this.selectedFile = file;
    this.newNameFile = file.nome;
    this.popupRenShow = true;
  }

  rename(evt:Event){
    if (['', null, undefined].indexOf(this.newNameFile.trim())){
      const pr = {
        oldPath: this.basePath+this.selectedFile.nome,
        newPath: this.basePath+this.newNameFile
      }

      this.documentoService.renFile(pr).subscribe({
         next: (res: any) => Util.showMsgSuccess(this.messageService,res),
         error: (err: any) => Util.showMsgErro(this.messageService,err),
         complete: () => {
           this.loading = false;
           this.popupRenShow = false;
           this.carregar();
         }
      });
    }
  }

  getPdf(base64:string, ext:string){
    const base64String = (ext=='pdf' ? `data:application/pdf;base64,${base64}` : `data:image/png;base64,${base64}`);
    return this.sanitizer.bypassSecurityTrustResourceUrl(base64String);
  }

  delDoc(evt:Event, file:any){
    this.confirmationService.confirm({
      target: evt.target as EventTarget,
      message: 'Deseja realmente excluir o arquivo: <br/><br/>' + file.nome + ' ?',
      header: 'Confirmação',
      closable: true,
      closeOnEscape: true,
      icon: 'pi pi-exclamation-triangle',
      rejectButtonProps: {label: 'Cancelar',severity: 'secondary'},
      acceptButtonProps: {label: 'Excluir',},
      accept: () => {
        let arrDel:string[]=[];
        arrDel.push(this.basePath+file.nome);
        this.deleteFileByName(arrDel);
      },
      reject: () => Util.showMsgErro(this.messageService,'You have rejected'),
    });
  }

  addSelect(f: any) {
    if(this.filesSelected.indexOf(f.nome) == -1)
      this.filesSelected.push(f.nome);
    else
      this.filesSelected = this.filesSelected.filter(nome => nome !== f.nome)
  }

  deletSelected(evt:Event){
    this.confirmationService.confirm({
      target: evt.target as EventTarget,
      message: `Deseja realmente excluir o(s) ${this.filesSelected.length} arquivo: ?`,
      header: 'Confirmação',
      closable: true,
      closeOnEscape: true,
      icon: 'pi pi-exclamation-triangle',
      rejectButtonProps: {label: 'Cancelar',severity: 'secondary'},
      acceptButtonProps: {label: 'Excluir',},
      accept: () => {
        let arrDel:string[]=[];
        this.filesSelected.forEach(n =>{
          arrDel.push(this.basePath+n);
        })
        this.deleteFileByName(arrDel);
      },
      reject: () => Util.showMsgErro(this.messageService,'You have rejected'),
    });
  }

  deleteFileByName(arrDel:string[]){
    this.loading = true;
    this.documentoService.delFiles(arrDel).subscribe({
      next: (res: any) => this.messageService.add({severity: 'success', summary: 'Success', detail: res}),
      error: (err: any) => this.messageService.add({severity: 'error', summary: 'Error', detail: JSON.stringify(err)}),
      complete: () => {
        this.filesSelected = [];
        this.carregar();
      }
    });
  }
}
