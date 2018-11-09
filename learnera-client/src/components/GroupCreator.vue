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
            <v-container>
              <v-layout row>
                <v-flex d-flex align-center justify-center>
                  <v-layout column>
                    <v-flex>
                      <h6 class="title">All users</h6>
                      <v-list/>
                    </v-flex>
                    <v-flex>
                      TODO: SHOW Users
                    </v-flex>
                  </v-layout>
                </v-flex>
                <v-divider class="mx-2" vertical/>
                <v-flex>
                  <v-layout column>
                    <v-flex>
                      <h6 class="title">Participants</h6>
                      <v-list/>
                    </v-flex>
                    <v-flex>
                      TODO: SHOW Users
                    </v-flex>
                  </v-layout>
                </v-flex>
              </v-layout>
            </v-container>
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

export default {
  name: 'GroupCreator',
  data() {
    return {
      groupName: '',
      groupDescription: '',
    };
  },
  computed: {
    ...mapState('account', ['user']),
    currentUser: state => state.user,
  },
  beforeMount() {
  },
  methods: {
    ...mapActions('groups', {
      createGroup: 'create'
    }),
    onGroupCreate() {
      const group = {
        curator: {id: this.currentUser.id},
        name: this.groupName,
        description: this.groupDescription,
        avatar: null, 
        students: null,
      };
      this.createGroup(group).then(group => {
        // TODO: IMPLEMENT NOTIFICATION
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
