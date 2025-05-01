import axios from "axios";
import {Fornecedor} from "../../../entity/Fornecedor.js";
import {Validate} from "../../../util/validate.js";
import {Tabela} from "../../../entity/Tabela.js"
import ApiCommon from "./fornecedor.js";

const URL = 'http://localhost:8081/api/v1/fornecedor';

export default {

    data() {
        return {
            valid:null,
            messages: [],
            fornecCad:Fornecedor,
            cidades:[],
            ufs:[],
            uf:'',
            cidade:{},
            cnpj:'',
            isCpf:false,
            busca:"",
            tabela:new Tabela(),
            loading:false
        };
    },
    methods:{
        async saveFornecedor(){
            this.messages = [];
            this.fornecCad.ibgeCod = this.cidade.ibgeCod;
            if(this.valid.notValidString(this.fornecCad.cpf) && this.isCpf)
                this.messages.push({ severity: 'warn', content: 'Faltando o CPF!' });
            if(this.valid.notValidString(this.fornecCad.cnpj) && !this.isCpf)
                this.messages.push({ severity: 'warn', content: 'Faltando o CNPJ'});
            if(this.valid.notValidString(this.fornecCad.nome))
                this.messages.push({ severity: 'warn', content: 'Nome inválido!'});
            if(this.valid.notValidString(this.fornecCad.razaoSocial))
                this.messages.push({ severity: 'warn', content: 'Razão Social inválida!'});

            this.save(this.fornecCad);
        },
        async searchCnpj(){
            this.loading = true;
            var path = URL+"/consultaCnpj/"+this.fornecCad.cnpj.replace(/[^0-9]+/g, "");
            const respCnpj = await axios.get(path)
                .then(response =>{
                    this.fornecCad.cnpj = response.data.cnpj.replace(/(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/g,"\$1.\$2.\$3\/\$4\-\$5");
                    this.fornecCad.nome = response.data.nome;
                    this.fornecCad.razaoSocial = response.data.razaoSocial;
                    this.fornecCad.ibgeCod = response.data.ibgeCod;

                    this.uf = response.data.cidade.UF;
                    this.buscaCidades();
                    this.cidade = this.cidades.filter(cid => cid.ibgeCod===response.data.cidade.ibgeCod)[0];

                }).catch(error => {
                    console.log(error);
                    this.$toast.add({ severity: 'error', summary: 'Error !', detail: error, life: 10000 });
                }).finally(() => this.loading = false);
        },
        save(){
            this.fornecCad;
            const headers = {"Content-Type": "application/json"};
            axios.post(URL, this.fornecCad, { headers })
                .then(response => {
                    this.$toast.add({ severity: 'success', summary: 'Success', detail: 'Fornecedor Salvo!', life: 3000 })
                    this.buscaFornecedor();
                    this.fornecCad = new Fornecedor();
                }).catch(error => {
                this.$toast.add({ severity: 'error', summary: 'Error !', detail: error.message, life: 3000 });
            }).finally(() => this.loading = false);
        },
        onCellEditComplete($event){
            //console.log(JSON.stringify($event.newData))
            this.loading = true;
            if(JSON.stringify($event.newData)!==JSON.stringify($event.data)){
                if($event.newData.cnpj && $event.newData.cpf){
                    this.$toast.add({ severity: 'error', summary: 'Error !', detail: 'só pode haver cnpj ou cpf !', life: 3000 });
                }else{
                    $event.newData.cidade = null;
                    this.fornecCad = $event.newData
                    this.save();
                }
            }
        },
        onSort($event){
            this.tabela.sort($event);
            this.buscaFornecedor();
        },
        onPage($event) {
            this.tabela.page($event);
            this.buscaFornecedor();
        },
        async buscaFornecedor(){
            this.tabela.loading = true;
            var path = URL+"/page?"+this.tabela.ordenacao;
            path += "&page="+this.tabela.pagina;
            path += "&size="+this.tabela.tamanho;
            path += "&busca="+this.busca;
            console.log(path)

            const respFonecedores = await axios.get(path);
            this.tabela.data = await respFonecedores.data.content;
            this.tabela.total = await respFonecedores.data.totalElements;
            this.tabela.loading = false;
        },
        async buscaCidades(){
            this.loading = true;
            axios.get('http://localhost:8081/api/v1/cidadeEstado/'+this.uf)
                .then(resp=>{
                    this.cidades = resp.data;
                }).finally(() => this.loading = false);
        }

    },
    async created() {
        try {
            this.valid = new Validate();
            this.fornecCad = new Fornecedor();
            this.tabela.ordenacao="sort=id,desc";

            this.tabela.busca = this.buscaFornecedor();
            this.tabela.URL = URL;

            await this.buscaFornecedor()
            await axios.get('http://localhost:8081/api/v1/cidadeEstado/estados')
                .then(resp =>{
                    this.ufs = resp.data;
                    this.uf = 'SP';
                    this.buscaCidades()

            });

        } catch (error) {
            console.error(error);
        }
    }
};