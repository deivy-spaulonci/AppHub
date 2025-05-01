import axios from "axios";
import {Despesa} from "../../../entity/Despesa.js";

const URL = 'http://localhost:8081/api/v1/';

export default {
    data() {
        return {
            despesas: [],
            tiposDespesas: [],
            formaPagamentos: [],
            fornecedores:[],
            despesa:Despesa
        };
    },
    methods:{
        formatDate(value){
            return value.split('-').reverse().join("/");
        },
        formatCurrency(value){
            return value.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
        },
        async search(event){
            let retorno = await axios.get(URL+'fornecedor/find/'+event.query);
            this.fornecedores = retorno.data;
        }

    },
    async created() {
        try {
            this.despesa = new Despesa();

            // const response = await axios.get(URL+'despesa/page');
            // this.despesas = response.data.content;
            const respTipoDespesa = await axios.get(URL+'tipos/tipo-despesa');
            this.tiposDespesas = respTipoDespesa.data;
            const respFormaPagamento = await axios.get(URL+'tipos/forma-pagamento');
            this.formaPagamentos = respFormaPagamento.data;

        } catch (error) {
            console.error(error);
        }
    }

};