<template>
  <div v-if="shown">
    <v-subheader class="red--text">{{ message }}</v-subheader>
  </div>
</template>

<script>
export default {
  model: {
    prop: 'shown',
    event: 'update:shown'
  },
  props: ['message', 'shown']
}
</script>
