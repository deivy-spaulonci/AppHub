<script setup>
import PanelHeader from "../compcommons/PanelHeader.vue";
</script>
<template>
  <Toast />

  <panel-header titulo="Fornecedores"></panel-header>

  <Card style="width: 1200px; margin: auto">
    <template #content>
      <!--      CADASTRO----------------------------------------------------------------------------------------------->
      <div id="form">
        <label for="cnpj">CNPJ:</label>
        <InputGroup style="width: 50%;">
          <InputMask name="cnpj" v-model="fornecCad.cnpj" mask="99.999.999/9999-99" :disabled="isCpf" />
          <Button icon="pi pi-globe" :disabled="isCpf" @click="searchCnpj"/>
        </InputGroup>

        <label>CPF:</label>
        <InputGroup style="width: 50%;">
          <InputText type="text" v-model="fornecCad.cpf" :disabled="!isCpf"/>
          <ToggleButton v-model="isCpf"  onLabel="CPF" offLabel="CPF" onIcon="pi pi-lock" offIcon="pi pi-lock-open" class="w-36"/>
        </InputGroup>

        <label for="cnpj">Nome:</label>
        <InputText type="text" v-model="fornecCad.nome"/>

        <label for="cnpj">Razão Social:</label>
        <InputText type="text" v-model="fornecCad.razaoSocial"/>

        <label for="cnpj">Cidade:</label>
        <div style="display: grid; grid-template-columns: 80px auto;">
          <Select placeholder="Estado" :options="ufs" v-model="uf" @change="buscaCidades"/>
          <Select placeholder="Cidade" :options="cidades" v-model="cidade" optionLabel="nome" filter/>
        </div>

        <label></label>
        <Button label="Salvar" style="width: 150px; margin-left: auto;" @click="saveFornecedor"/>
      </div>
      <!--      CONSULTA----------------------------------------------------------------------------------------------->
      <DataTable :value="tabela.data"
                 dataKey="id"
                 stripedRows
                 editMode="cell"
                 @cell-edit-complete="onCellEditComplete"
                 v-model:selection="tabela.selected"
                 selectionMode="single"
                 :loading="tabela.loading"
                 @sort="onSort($event)"
                 size="small">
        <template #header>
          <Toolbar class="noBorder">
            <template #end>
              <InputText type="text" v-model="busca" />
              <Button type="button" icon="pi pi-filter" label="Buscar" outlined @click="buscaFornecedor"/>
            </template>
          </Toolbar>
        </template>
        <Column field="id" header="Id" sortable ></Column>
        <Column field="nome" header="Nome" sortable>
          <template #body="slotProps">{{slotProps.data.nome.slice(0,40)}}</template>
          <template #editor="{ data, field }"><InputText autofocus fluid v-model="data[field]"/></template>
        </Column>
        <Column field="razaoSocial"  header="Razão Social" sortable>
          <template #body="slotProps">{{slotProps.data.razaoSocial.slice(0,40)}}</template>
          <template #editor="{ data, field }"><InputText autofocus fluid v-model="data[field]"/></template>
        </Column>
        <Column field="cnpj" header="CNPJ" sortable>
          <template #body="slotProps" >
            <div v-if="slotProps.data.cnpj">
              {{slotProps.data.cnpj.replace(/(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/g,"\$1.\$2.\$3\/\$4\-\$5")}}
            </div>
          </template>
        </Column>
        <Column field="cpf" header="CPF" sortable>
          <template #body="slotProps">{{slotProps.data.cpf}}</template>
          <template #editor="{ data, field }"><InputText autofocus fluid v-model="data[field]"/></template>
        </Column>
        <Column field="cidade" header="Cidade" sortable>
          <template #body="slotProps">
            {{slotProps.data.cidade.nome + "-" + slotProps.data.cidade.uf}}
          </template>
        </Column>
        <template #empty> nenhum fornecedor encontrado. </template>
      </DataTable>
      <Paginator :rows="tabela.tamanho"
                 :totalRecords="tabela.total"
                 :currentPage="tabela.pagina"
                 @page="onPage($event)"
                 :rowsPerPageOptions="tabela.linhasPorPag"></Paginator>

      <Message v-for="msg of messages" :key="msg.id" :severity="msg.severity" class="mt-4">{{ msg.content }}</Message>

    </template>
    <template #footer>
      <div style="text-align: center; padding: 5px;">

      </div>
    </template>
  </Card>
  <Dialog v-model:visible="loading" modal :closable="false">
    <ProgressSpinner style="width: 50px; height: 50px" strokeWidth="8" fill="transparent"
                     animationDuration=".5s" aria-label="Custom ProgressSpinner" />
  </Dialog>
</template>

<script>
import fornecedor from "./fornecedor.js";

export default {
  mixins:[fornecedor]
}
</script>

<style scoped>
#form {
  display: grid;
  grid-template-columns: 100px 400px 100px 400px;
  grid-gap: 10px;
  margin-bottom: 10px;
}
</style>