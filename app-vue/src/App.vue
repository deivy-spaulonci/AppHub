<style>
@import './style.css';
</style>

<script setup>
import {ref} from "vue";
import {RouterLink, RouterView} from 'vue-router'

const items = ref([
  {label: 'Home', icon: 'pi pi-home', url: '/'},
  {label: 'Documentos', icon: 'pi pi-file'},
  {
    label: 'Financeiro', icon: 'pi pi-wallet',
    items: [
      {label: 'Despessas', icon: 'pi pi-dollar', url: '/despesas'},
      {
        label: 'Contas', icon: 'pi pi-barcode', items: [
          {label: 'Tipo Conta', icon: 'pi pi-cog'}
        ]
      },
      {label: 'Fonrecedores', icon: 'pi pi-box', url: '/fornecedores'}
    ]
  }
]);
</script>

<template>
  <Menubar :model="items"/>
  <RouterView/>
</template>

<style scoped></style>

