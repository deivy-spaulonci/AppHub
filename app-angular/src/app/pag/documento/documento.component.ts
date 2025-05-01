import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {MessageService, TreeNode} from 'primeng/api';
import {DefaultService} from '../../service/default.service';
import {Panel} from 'primeng/panel';
import {Tree} from 'primeng/tree';
import {Diretorio} from '../../model/diretorio';
import {Button} from 'primeng/button';
import {BlockUI} from 'primeng/blockui';
import {ProgressSpinner} from 'primeng/progressspinner';
import {Toast} from 'primeng/toast';

@Component({
  selector: 'app-documento',
  imports: [
    Panel,
    Tree,
    Button,
    BlockUI,
    ProgressSpinner,
    Toast
  ],
  templateUrl: './documento.component.html',
  standalone: true,
  styleUrl: './documento.component.css',
  providers: [MessageService],
})
export class DocumentoComponent implements OnInit{
  files!: TreeNode[];
  loading: boolean = false;
  selectedFile!: TreeNode;

  constructor(private defaultService: DefaultService,
              private cd: ChangeDetectorRef,
              private messageService: MessageService,) {}

  ngOnInit() {
    this.loading=true;
    this.defaultService.get('documentos/listar-arquivos').subscribe({
      next: res => {
        this.files = this.diretoriosParaTreeNodes(res);
      },
      error: error => {
        this.messageService.add({severity: 'error', summary: 'Error', detail: 'consulta de documentos'});
      },
      complete: () => {
        this.loading=false;
      }
    });
  }

  // Função para converter a estrutura de Diretorio para TreeNode
  diretorioParaTreeNode(diretorio: Diretorio, parentNode: TreeNode | null = null): TreeNode {
    const node:TreeNode={};
    node.label=diretorio.nome;
    node.data=diretorio;
    node.key=diretorio.nome;
    node.children=[]
    node.leaf=diretorio.subDiretorios.length === 0 && diretorio.arquivos.length === 0;
    node.parent=parentNode!;
    node.expanded=false;
    node.icon='pi pi-folder';
    node.expandedIcon='pi pi-folder-open';
    node.collapsedIcon='pi pi-folder';


//    Adicionando subdiretórios como filhos
    if (diretorio.subDiretorios.length > 0) {
      node.children = diretorio.subDiretorios.map(subDiretorio => this.diretorioParaTreeNode(subDiretorio, node));
    }

    // Adicionando arquivos como filhos também, representados como "leaf nodes" (nós folha)
    if (diretorio.arquivos.length > 0) {
      diretorio.arquivos.forEach(arquivo => {
        node.children?.push({
          label: arquivo, // O nome do arquivo será o label
          data: arquivo, // O dado completo do arquivo
          key: arquivo, // A chave única do arquivo pode ser o nome
          leaf: true, // Arquivos não têm filhos, então são nós folha
          icon: 'pi pi-file', // O ícone para um arquivo
        });
      });
    }

    return node;
  }

  // Função para converter a lista de diretórios
  diretoriosParaTreeNodes(diretorios: any[]): TreeNode[] {
    return diretorios.map(diretorio => this.diretorioParaTreeNode(diretorio));
  }

  expandAll() {
    this.files.forEach((node) => {
      this.expandRecursive(node, true);
    });
  }

  collapseAll() {
    this.files.forEach((node) => {
      this.expandRecursive(node, false);
    });
  }

  expandRecursive(node: TreeNode, isExpand: boolean) {
    node.expanded = isExpand;
    if (node.children) {
      node.children.forEach((childNode) => {
        this.expandRecursive(childNode, isExpand);
      });
    }
  }

  nodeSelect(event: any) {
    if(event.node.leaf)
      console.log(event.node)
    // if(event.node.leaf==0)

    //this.messageService.add({ severity: 'info', summary: 'Node Selected', detail: event.node.label });
  }

}
