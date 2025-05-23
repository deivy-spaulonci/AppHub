import axios from "axios";

export class ApiCommon{
    BASE = 'http://localhost:8081/api/v1/';

    async buscaCidades(uf){
        axios.get(this.BASE+'cidadeEstado/'+uf)
            .then(resp=>{
                //this.cidades = resp.data;
                return resp.data;
            });//.finally(() => this.loading = false);
    }
}
