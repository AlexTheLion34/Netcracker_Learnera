<template>
  <v-layout row wrap>
    <v-list v-if="currentUser" two-line>
      <v-list-tile
        v-for="group in groups"
        :key="group.id"
        :to="`/group/${group.id}`"
        avatar
      >
        <v-list-tile-avatar>
          <v-flex>
            <v-avatar color="teal">
              <img v-if="group.avatar" :src="group.avatar">
              <span v-else class="white--text headline">{{ group.name[0] }}</span>
            </v-avatar>
          </v-flex>
        </v-list-tile-avatar>

        <v-list-tile-content>
          <v-list-tile-title>{{ group.name }}</v-list-tile-title>
          <v-list-tile-sub-title>{{ group.description }}</v-list-tile-sub-title>
        </v-list-tile-content>

        <v-list-tile-action>
          <v-tooltip bottom>
            <v-btn slot="activator" icon ripple>
              <v-icon color="grey lighten-1">build</v-icon>
            </v-btn>
            <span>Edit group</span>
          </v-tooltip>
          <v-tooltip bottom>
            <v-btn slot="activator" icon ripple>
              <v-icon color="red lighten-1">delete</v-icon>
            </v-btn>
            <span>Delete group</span>
          </v-tooltip>
        </v-list-tile-action>
      </v-list-tile>
    </v-list>
  </v-layout>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex'
import {store} from '../store'

export default {
  name: 'GroupsList',
  props: ['user'],
  data() {
    return {
    };
  },
  computed: {
    ...mapState('account', {
      currentUser: state => state.user
    }),
    groups: function() {
        if (this.currentUser.role == 'TEACHER') {
            return this.currentUser.curatedGroups
        }    
        return this.currentUser.studyGroups
    }
  }
  }
</script>