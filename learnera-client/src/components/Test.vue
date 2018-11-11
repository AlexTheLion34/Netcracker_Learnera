<template>
  <v-container fluid>
    <v-layout column>
      Hello world!
      <v-text-field v-model="obj.objs[0].message"/>
      <v-btn @click="obj = {objs: [{message: 'OK'}]}">{{ obj.objs[0].message }}</v-btn>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: 'Test',
  data() {
    return {
      message: 'Hello',
      obj: {
        message: 'Ni hao',
        obj: {
        },
        objs: []
      }
    }
  },
  beforeMount() {
    this.obj.objs = [{message: 'Hello1'}];
  }
}
</script>

<style>

</style>
