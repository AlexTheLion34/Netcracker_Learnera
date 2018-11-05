<template>
    <v-list>
            <v-toolbar color="grey" dark>
                <v-toolbar-title>My Groups</v-toolbar-title>
                <v-btn icon>
                    <v-icon>add</v-icon>
                </v-btn>
            </v-toolbar>
                    <v-list-tile
        v-for="group in groups"
        :key="group.id"
        :to="`/group/${group.id}`"
        avatar
      >
                        <v-list-tile-avatar>
                            <img :src="group.avatar">
                        </v-list-tile-avatar>
                        <v-list-tile-content>
                            <v-list-tile-title v-html="group.name"></v-list-tile-title>
                            <v-list-tile-sub-title v-html="group.description"></v-list-tile-sub-title>
                        </v-list-tile-content>
                    </v-list-tile>
        </v-list>
</template>

<script>
import {mapState} from 'vuex'
export default {
    name : 'Groups',
    data () {
        return {
            
        }
    },
    computed: {
        groups() { return this.user.studyGroups },
        ...mapState('account', ['user']),
  }
}
</script>
