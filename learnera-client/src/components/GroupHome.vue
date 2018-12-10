<template>
    <v-container v-if="group">
        <v-layout row wrap>
      <v-textarea v-if="group.description" 
                  :value="group.description"
                  box
                  label="Description"
                  auto-grow
                  readonly/>
      <v-flex xs12><v-divider style="margin: -1.2em 0 1em 0;"/></v-flex>
      <v-flex xs12>
        <v-card>
          <v-card-title>
                <h3 class="headline mb-0">Students</h3>
                  <v-spacer></v-spacer>
                    <v-text-field
                      v-model="search"
                      append-icon="search"
                      label="Search"
                      single-line
                      hide-details
                    ></v-text-field>
                </v-card-title>
          <v-responsive>
            <v-data-table
              :headers="[
                {text: 'Student', value: 'info.firstName'}, 
                {text: 'Email', value: 'email'}, 
              ]"
              :pagination.sync="pagination"
              :search="search"
              :items="students"
              hide-actions
              class="elevation-1"
            >
              <template slot="items" slot-scope="props">
                <td>
                  <v-avatar color="teal"  size="36px">
                    <img v-if="props.item.avatar" :src="props.item.avatar">
                    <span v-else class="white--text headline">{{props.item.info.firstName[0]}}</span>
                  </v-avatar>
                  {{props.item.info.firstName + " " + props.item.info.lastName}}
                  </td>
                  <td>
                    {{ props.item.email}}
                  </td>
              </template>
            </v-data-table>
            <div class="text-xs-center pt-2">
              <v-pagination v-model="pagination.page" :length="pages"></v-pagination>
            </div>
          </v-responsive>
        </v-card>
      </v-flex>
      </v-layout>
    </v-container>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex';
import {store} from '../store'

export default {
  name: 'GroupHome',
  model: {
    prop: 'group',
    event: 'group-changed'
  },
  props: ['group'],
  data() {
    return {
      search: '',
      pagination: {
        rowsPerPage: 5,
      },
    };
  },
  computed: {
    ...mapState('account', ['user']),
    ...mapState('users', {
      students: function(state) {
        var ret = state.items.filter(s => s.role === 'STUDENT')
        ret = ret.filter(r => this.group.students.indexOf(r.id) != -1)
        return ret
    },
      curator: function(state) {
        if (!this.group) {
          return undefined;
        }
        const ret = state.items.find(i => i.id === this.group.curator);
        return ret;
      }
    }),
    groupId: function() {
      return parseInt(this.groupIdStr);
    },
    pages () {
      return Math.ceil(this.students.length / this.pagination.rowsPerPage)
    }
  },
  watch: {
    group: function(val) {
      if (val) {
        this.getUser(val.curator)
      }
    }
  },
  methods: {
    ...mapActions('groups', {
      getGroup: 'get'
    }),
    ...mapActions('users', {
      getStudents: 'getByStudyGroupId',
      getUser: 'get'
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
