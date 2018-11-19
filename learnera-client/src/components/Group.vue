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
              <v-flex d-flex justify-center align-center class="head">{{ group.name ? group.name : '' }}</v-flex>
              <v-flex d-flex justify-center align-center>
                <v-btn v-if="curator" :to="`/user/${curator.id}`" color="primary">
                  CURATOR
                </v-btn>
              </v-flex>
            </v-layout>
          </div>
          <v-spacer/>
        </v-layout>
      </v-flex>
      <v-flex xs12><v-divider style="margin: 1em 0 1em 0;"/></v-flex>
      <v-flex xs12>
        <router-view v-model="group"/>
      </v-flex>
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
  },
  watch: {
    groupId: function(val) {
      this.getGroup(val);
    },
    group: function(val) {
      if (val) {
        this.getUser(val.curator)
      }
    }
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
