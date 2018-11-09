<template>
  <v-container v-if="user">
    <v-layout column>
      <v-flex>
        <v-layout row justify-start>
          <v-avatar size="100" color="teal">
            <img v-if="user.info && user.info.avatar" :src="course.info.avatar">
            <span v-else-if="user.info && user.info.firstName" class="ava">{{ user.info.firstName[0] }}</span>
            <span v-else-if="user.info && user.info.lastName" class="ava">{{ user.info.lastName[0] }}</span>
            <span v-else-if="user.email" class="ava">{{ user.email[0] }}</span>
          </v-avatar>
          <div class="mainInfo">
            <v-layout column fill-height justify-space-between>
              <v-flex>
                <span class="head">
                  {{ 
                    (user.info && user.info.firstName + user.info.lastName) 
                      ? user.info.firstName + ' ' + user.info.lastName : user.email ? user.email : '' 
                  }}
                </span>
              </v-flex>
              <div v-if="user.role === 'TEACHER'">
                <v-chip small color="orange" text-color="white">
                  Teacher
                  <v-icon right>star</v-icon>
                </v-chip>
              </div>
              <div v-else-if="user.role === 'STUDENT'">
                <v-chip small color="blue" text-color="white">
                  Student
                  <v-icon right>person</v-icon>
                </v-chip>
              </div>
            </v-layout>
          </div>
          <v-spacer/>
          <v-flex xs3>
            <v-layout row>
              <v-flex xs6>
                <v-layout column>
                  <v-btn :to="`/user/${userId}`" round active-class>User home</v-btn>
                  <v-btn :to="`/user/${userId}/courses`" round>Courses</v-btn>
                </v-layout>
              </v-flex>
              <v-flex xs6>
                <v-layout column>
                  <v-btn :to="`/user/${userId}/groups`" round>Groups</v-btn>
                  <v-btn :to="`/user/${userId}/templates`" :disabled="user.role !== 'TEACHER'" round>Templates</v-btn>
                </v-layout>
              </v-flex>
            </v-layout>
          </v-flex> 
        </v-layout>
      </v-flex>
      <v-divider style="margin: 5px;"/>
      <v-flex>
        <router-view/>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex';

export default {
  name: 'User',
  props: ['userIdStr'],
  computed: {
    userId: function() {
      return parseInt(this.userIdStr);
    },
    ...mapState('users', {
      user(state) { 
        const ret = state.items.find(x => x.id === this.userId);
        return ret;
      }
    })
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

<style scoped>
.ava {
  font-size: 5em;
  color: white;
  text-align: center;
}

.mainInfo {
  margin-left: 0.8em;
}

.head {
  font-size: 1.5em;
  color: black;
}
</style>
