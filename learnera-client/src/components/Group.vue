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
                {text: 'Name', value: ''}, 
                {text: 'Points', value: ''}, 
              ]"
              :items="studentsNames"
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
    ...mapState('groups', {
      group: function(state) {
        const ret = state.items.find(i => i.id === this.groupId && !i.loading);
        return ret;
      }
    }),
    ...mapState('account', ['user']),
    ...mapState('users', {
      students: function(state) {
        const ret = state.items.filter(s => s.role === 'STUDENT')
        return ret
    },
    }),
    studentsNames: function() {
      var infos = []
      this.students.forEach(student => {
        name = student.info ? 'name: ' + student.info.firstName + ' ' + student.info.lastName  : ''
        infos.push(name)
      });
      return infos
    },
    groupId: function() {
      return parseInt(this.groupIdStr);
    },
  },
  watch: {
    groupId: function(val) {
      this.getGroup(val);
    },
  },
  beforeMount() {
    this.getGroup(this.groupId);
    this.getStudents(this.groupId);
  },
  methods: {
    ...mapActions('groups', {
      getGroup: 'get'
    }),
    ...mapActions('users', {
      getStudents: 'getByStudyGroupId'
    })
  },
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
