<template>
  <v-container fluid grid-list-md text-xs-center>
    <v-layout row wrap>
        <v-flex>
            <groups-list/>
        </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import groupsList from './GroupsList'

export default {
    components: {'groups-list': groupsList}
}
</script>
