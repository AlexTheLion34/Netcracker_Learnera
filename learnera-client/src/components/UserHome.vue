<template>
  <v-layout>
            <v-flex>
                <!-- <v-card> -->
                <v-layout>
                    <v-flex xs6><h3 class="display-2">Groups</h3></v-flex>
                </v-layout>
                <v-layout>
                    <groups-list :user="user"/>
                </v-layout>
                <!-- </v-card> -->
            </v-flex>
            <v-flex>
                <!-- <v-card> -->
                <v-layout>
                    <v-flex xs6><h3 class="display-2">Groups</h3></v-flex>
                </v-layout>
                <v-layout>
                    <user-course-list :user="user"/>
                </v-layout>
                <!-- </v-card> -->
            </v-flex>
        </v-layout>
</template>

<script>
import {mapState, mapActions} from 'vuex'
import GroupsList from './GroupsList.vue'
import UserCourseList from './base/UserCourseList.vue'

export default {
  // TODO: IMPLEMENT USER HOME PAGE
  components: {GroupsList, UserCourseList},
  name: 'UserHome',
  props: ['userIdStr'],
  computed: {
    ...mapState('account', {
      currentUser: state => state.user
    }),
    ...mapState('users', {
      user(state) { 
        const ret = state.items.find(x => x.id === this.userId);
        return ret;
      }
    }),
    userId: function() {
      return parseInt(this.userIdStr);
    },
  },
  watch: {
    userIdStr: function() { 
      this.getUser(this.userId);
    }
  },
  created() {
    this.getUser(this.userId);
  },
  methods: {
    ...mapActions('users', {
      getUser: 'get'
    })
  }
}
</script>
