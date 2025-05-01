<script setup>

defineProps({
  titulo: String,
});
</script>
<template>
  <Toolbar class="noBorder">
    <template #start></template>
    <template #center><b>{{titulo}}</b></template>
    <template #end></template>
  </Toolbar>
</template>
<style scoped></style>