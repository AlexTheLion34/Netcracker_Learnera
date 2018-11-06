<template>
  <v-layout>
            <v-flex>
                <!-- <v-card> -->
                <v-layout>
                    <v-flex xs6><h3 class="display-2">Groups</h3></v-flex>
                </v-layout>
                <v-layout>
                    <groups-list user="currentUser"/>
                </v-layout>
                <!-- </v-card> -->
            </v-flex>
            <v-flex>
                <!-- <v-card> -->
                <v-layout>
                    <v-flex xs6><h3 class="display-2">Courses</h3></v-flex>
                </v-layout>
                <v-layout>
                    <teacher-course-list v-if="currentUser && currentUser.role === 'TEACHER'" :user="currentUser"/>
                    <student-course-list v-if="currentUser && currentUser.role === 'STUDENT'" :user="currentUser"/>
                </v-layout>
                <!-- </v-card> -->
            </v-flex>
        </v-layout>
</template>

<script>
import {mapState, mapActions} from 'vuex'
import GroupsList from './GroupsList.vue'
export default {
  // TODO: IMPLEMENT USER HOME PAGE
  name: 'UserHome',
  components: {GroupsList},
  computed: {
    ...mapState('account', {
      currentUser: state => state.user
    })
  }
}
</script>
