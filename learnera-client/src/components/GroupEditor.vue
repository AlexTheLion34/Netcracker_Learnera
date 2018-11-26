<template>
  <v-container v-if="group">
    <v-layout row wrap>
      <v-textarea v-if="group.description" 
                  v-model="group.description"
                  box
                  label="Description"
                  auto-grow/>
      <v-flex xs12><v-divider style="margin: -1.2em 0 1em 0;"/></v-flex>
      <v-flex xs12>
        <v-btn @click="saveGroup" block color="primary" dark>
          <strong v-if="curator && curator.id === user.id">Save group</strong>
        </v-btn>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex';
import {store} from '../store'

export default {
  name: 'GroupEditor',
  model: {
    prop: 'group',
    event: 'group-changed'
  },
  props: ['group'],
  data() {
    return {
    };
  },
  computed: {
    ...mapState('account', ['user']),
    ...mapState('users', {
      curator: function(state) {
        if (!this.group) {
          return undefined;
        }
        const ret = state.items.find(i => i.id === this.group.curator);
        return ret;
      }
    }),
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
      getGroup: 'get',
      updateGroup: 'update'
    }),
    ...mapActions('users', {
      getUser: 'get'
    }),
    saveGroup() {
      let {curator, students, courses, ...other} = this.group

      const ret = {
        ...other,
        students: students.map(x => ({id: x})),
        courses: courses.map(x => ({id: x.id})),
        curator: {id: this.curator.id},
      }

      console.log('Group: ', JSON.stringify(ret));
      this.updateGroup(ret).then(x => {
        this.$emit('group-changed', x)
      })
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
