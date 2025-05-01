<script setup>
import PanelHeader from "./view/compcommons/PanelHeader.vue";
</script>

<template>

  <panel-header titulo="Home"></panel-header>

  <Card style="width: 50rem; overflow: hidden">
    <template #title>Simple Card</template>
    <template #content>
      <p class="m-0">
        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Inventore sed
        quas!
      </p>
    </template>
  </Card>
  <Card style="width: 50rem; overflow: hidden">
    <template #title>Simple Card</template>
    <template #content>
      <p class="m-0">
        Lorem ipsum dolor sit amet, consectetur adipisicing elit.
        quas!
      </p>
    </template>
  </Card>
</template>

<style scoped>
</style>