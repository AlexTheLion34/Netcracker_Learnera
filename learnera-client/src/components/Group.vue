<template>
  <v-container v-if="group">
    <v-layout row wrap>
      <v-flex xs12>
        <v-layout row>
          <v-avatar size="100" color="teal">
            <img v-if="group.avatar" :src="group.avatar">
            <span v-else-if="group.name" class="ava">{{ group.name[0] }}</span>
          </v-avatar>
            <div>
            <v-layout justify-space-between column fill-height>
                <v-flex d-flex justify-center align-center>
                     <v-flex d-flex justify-center align-center class="head">{{ group.name ? group.name : '' }}</v-flex>
                </v-flex>
            </v-layout>
          </div>
          <v-spacer/>
        </v-layout>
      </v-flex>
      <v-flex xs12><v-divider style="margin: 1em 0 1em 0;"/></v-flex>
      <v-textarea v-if="group.description" 
                  :value="group.description"
                  box
                  label="Description"
                  auto-grow
                  readonly/>
      <v-flex xs12><v-divider style="margin: -1.2em 0 1em 0;"/></v-flex>
      <v-flex xs12>
        <v-card>
          <v-card-title><h3 class="headline mb-0">Students</h3></v-card-title>
          <v-responsive>
            <v-data-table
              :headers="[
                {text: 'Id', value: ''}, 
                {text: 'Points', value: ''}, 
              ]"
              :items="id"
              hide-actions
              class="elevation-1"
            >
              <template slot="items" slot-scope="props">
                <td>{{ props.item }}</td>
              </template>
            </v-data-table>
          </v-responsive>
        </v-card>
      </v-flex>
      <v-flex xs12><v-divider style="margin: 1em 0 1em 0;"/></v-flex>
      <!-- <v-flex xs12>
        <v-card>
          <v-card-title><h3 class="headline mb-0">Discussion</h3></v-card-title>
        </v-card>
        <v-btn fluid>Send</v-btn>
      </v-flex> -->
    </v-layout>
  </v-container>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex';
import {store} from '../store'

export default {
  name: 'Group',
  props: ['groupIdStr'],
  data() {
    return {
    };
  },
  computed: {
      currentUser: state => state.user,
    ...mapState('groups', {
      group: function(state) {
        if (this.currentUser.role == "TEACHER") {
            return this.currentUser.curatedGroups[this.groupIdStr - 1]
        } else {
            return this.currentUser.studyGroups[this.groupId - 1]
        }
      }
    }),
    ...mapState('account', ['user']),
    ...mapState('users', {
    id: function() {
      return this.group.students
    }
    }),
  },
  methods: {
    ...mapActions('groups', {
      getCourse: 'get'
    }),
    ...mapActions('users', {
      getUser: 'get'
    })
  }
}
</script>

<style>
.ava {
  font-size: 5em;
  color: white;
  text-align: center;
}

.mainInfo {
  padding-left: 0.4em;
}

.head {
  font-size: 1.5em;
  color: black;
}

</style>
