import axios from "axios";

export class Tabela{
    // URL = "";
    data= [];
    total= 0;
    loading= false;

    pagina= 0;
    tamanho = 15;
    linhasPorPag= [15,20,30,40];

    ordenacao = "";

    selected= {};

    sort($event){
        this.ordenacao ="";
        this.ordenacao = "sort="+$event.sortField+",";
        this.ordenacao += ($event.sortOrder===1 ? 'asc' : 'desc');
    }
    page($event){
        this.pagina = $event.page;
        this.tamanho = $event.rows;
    }
}