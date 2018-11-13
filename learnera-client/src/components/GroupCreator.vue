<template>
  <v-container>
    <v-layout row wrap>
      <v-flex d-flex justify-center align-center xs8>
        <h3 class="headline">Create group</h3>
      </v-flex>
      <v-flex xs12>
        <v-text-field v-model="groupName" label="Group name"/>
      </v-flex>
      <v-flex xs12>
        <v-layout row>
          <v-flex>
            <v-avatar size="100" color="teal">
              <span>TODO</span>
            </v-avatar>
            <v-btn>Load avatar</v-btn>
          </v-flex>
        </v-layout>
      </v-flex>
      <v-flex style="margin: 15px 0 0 0">
        <v-textarea v-model="groupDescription" box label="Group description" auto-grow/>
      </v-flex>
      <v-flex xs12 style="margin: 15px 0 0 0">
        <v-card>
          <v-card-title><h3 class="headline mb-0">User management</h3></v-card-title>
          <v-responsive>
            <two-lists-selector :items="students" :list-renderer="UserList" header-all="All students" header-selected="Participants"
                                @update:selected-items="selectedUsers = $event" />
          </v-responsive>
        </v-card>
      </v-flex>
      <v-flex xs12 style="margin: 15px 0 0 0">
        <v-card>
          <v-card-title><h3 class="headline mb-0">Confirm your action</h3></v-card-title>
          <v-responsive>
            <v-layout row>
              <v-flex style="margin: 0 10px 0 10px" xs6>
                <v-btn color="primary" block @click="onGroupCreate">Create group</v-btn>
              </v-flex>
              <v-flex style="margin: 0 10px 0 10px" xs6>
                <v-btn color="secondary" block @click="
                $router.back()">Cancel</v-btn>
              </v-flex>
            </v-layout>
          </v-responsive>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex';
import {store} from '../store'
import {router} from '../router'
import UserList from './base/UserList.vue'
import TwoListsSelector from './base/TwoListsSelector.vue'

export default {
  name: 'GroupCreator',
  components: {UserList, TwoListsSelector},
  data() {
    return {
      groupName: '',
      groupDescription: '',
      selectedUsers: [],
      UserList
    };
  },
  computed: {
    ...mapState('account', {
      currentUser: state => state.user
    }),
  ...mapState('users', {
    user(state) {
      const ret = state.items.find(x => x.id === this.userId);
      return ret;
    },
    students(state) {
      const ret = state.items.filter(s => s.role === 'STUDENT')
      return ret
    },
    studentsId: function() {
      var ids = []
      this.selectedUsers.forEach(student => {
        ids.push({id: student.id})
      });
      return ids
    }
  }),
  },
  beforeMount() {
    this.getUser(this.currentUser.id);
    this.getAllStudents()
  },
  methods: {
    ...mapActions('groups', {
      createGroup: 'create'
    }),
    ...mapActions('users', {
      getUser: 'get',
      getAllStudents: 'getAllStudents',
    }),
    onGroupCreate() {
      const group = {
        curator: {id: this.currentUser.id},
        name: this.groupName,
        description: this.groupDescription,
        avatar: null, 
        students: this.studentsId,
      };
      this.createGroup(group).then(group => {
        router.push(`/group/${group.id}`);
      }).catch(e => console.error);
    }
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
