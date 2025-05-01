<script setup>
defineProps({
  valores: Array,
});
</script>

<template>
  <Select :options="valores"
          filter
          scrollHeight="400px"
          optionLabel="nome"
          placeholder="selecionar"/>
</template>

<style scoped>

</style>