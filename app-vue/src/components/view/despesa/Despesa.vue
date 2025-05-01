<script setup>
import PanelHeader from "../compcommons/PanelHeader.vue";
import {ref} from "vue";
import DropDown from "../compcommons/DropDown.vue";

const visible = ref(false);
const value = ref("");
</script>

<template>
  <panel-header titulo="Despesas"></panel-header>

  <Tabs value="0" style="width: 80em; margin: auto">
    <TabList>
      <Tab value="0">Consulta</Tab>
      <Tab value="1">Cadastro</Tab>
    </TabList>
    <TabPanels>
      <TabPanel value="0">
<!--        <DataTable :value="despesas"-->
<!--                   stripedRows-->
<!--                   size="small"-->
<!--                   tableStyle="min-width: 50rem">-->
<!--          <template #header>-->
<!--            <Toolbar class="noBorder">-->
<!--              <template #end>-->
<!--                <Button type="button" icon="pi pi-filter" label="Filtros" outlined @click="visible = true"/>-->
<!--                <Divider layout="vertical"/>-->
<!--                <Button type="button" icon="pi pi-filter-slash" label="Limpar" outlined/>-->
<!--              </template>-->
<!--            </Toolbar>-->
<!--          </template>-->
<!--          <Column field="id" header="Code" sortable></Column>-->
<!--          <Column field="tipoDespesa" header="Despesa">-->
<!--            <template #body="slotProps">-->
<!--              {{ slotProps.data.tipoDespesa.nome  }}-->
<!--            </template>-->
<!--          </Column>-->
<!--          <Column field="fornecedor" header="Fornecedor">-->
<!--            <template #body="slotProps">-->
<!--              {{ slotProps.data.fornecedor.nome  }}-->
<!--            </template>-->
<!--          </Column>-->
<!--          <Column field="data" header="Data">-->
<!--            <template #body="slotProps">-->
<!--              {{ formatDate(slotProps.data.dataPagamento) }}-->
<!--            </template>-->
<!--          </Column>-->
<!--          <Column field="formaPagamento" header="Forma Pgto">-->
<!--            <template #body="slotProps">-->
<!--              {{ slotProps.data.formaPagamento.nome  }}-->
<!--            </template>-->
<!--          </Column>-->
<!--          <Column field="valor" header="Valor" style="text-align: right;">-->
<!--            <template #body="slotProps">-->
<!--              {{ formatCurrency(slotProps.data.valor) }}-->
<!--            </template>-->
<!--          </Column>-->
<!--        </DataTable>-->

      </TabPanel>
      <TabPanel value="1">
        <Card>
          <template #content>
            <div id="form">
              <label>Tipo Despesa:</label>
              <drop-down :valores="tiposDespesas" v-model="despesa.tipoDespesa"></drop-down>
              <label>Fornecedor:</label>
              <AutoComplete :suggestions="fornecedores"
                            @complete="search"
                            :delay="600"
                            optionLabel="nome"
                            :inputStyle="{width:'100%'}"
                            v-model="despesa.fornecedor">
                <template #option="slotProps">
                  {{ slotProps.option.nome ? slotProps.option.nome : slotProps.option.razaoSocial }}
                  {{ slotProps.option.cnpj ? (" | " + slotProps.option.cnpj) : ''}}
                </template>
              </AutoComplete>
              <label>Data:</label>
              <InputMask id="basic" mask="99/99/9999" placeholder="**/**/****"/>
              <label>Valor:</label>
              <InputNumber mode="currency" fluid locale="pt-BR" currency="BRL"/>
              <label>Forma Pagamento:</label>
              <drop-down :valores="formaPagamentos"></drop-down>
              <label>Obs:</label>
              <Textarea rows="3" cols="30"/>
            </div>
          </template>
          <template #footer>
            <div class="footForm">
              <Button label="Salvar"/>
              <Button label="Cancelar" severity="secondary"/>
            </div>
          </template>
        </Card>
      </TabPanel>
    </TabPanels>
  </Tabs>

  <Dialog v-model:visible="visible" modal header="Edit Profile" :style="{ width: '25rem' }">
    <span class="text-surface-500 dark:text-surface-400 block mb-8">Update your information.</span>
    <div class="flex items-center gap-4 mb-4">
      <label for="username" class="font-semibold w-24">Username</label>
      <InputText id="username" class="flex-auto" autocomplete="off"/>
    </div>
    <div class="flex items-center gap-4 mb-8">
      <label for="email" class="font-semibold w-24">Email</label>
      <InputText id="email" class="flex-auto" autocomplete="off"/>
    </div>
    <div class="flex justify-end gap-2">
      <Button type="button" label="Cancel" severity="secondary" @click="visible = false"></Button>
      <Button type="button" label="Save" @click="visible = false"></Button>
    </div>
  </Dialog>

</template>

<script>
import despesa from "./despesa.js";

export default {
  mixins:[despesa]
}

</script>

<style scoped>
.footForm {
  width: 50%;
  margin: 0 auto;
  display: grid;
  grid-template-columns: auto auto;
  align-self: center;
  gap: 10px;
}

#form {
  display: grid;
  grid-template-columns: 120px 300px;
  grid-gap: 10px;
}
</style>