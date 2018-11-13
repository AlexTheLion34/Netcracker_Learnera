<template>
  <v-container>
    <v-layout row wrap>
      <v-flex xs12><h3 class="display-2">Templates</h3></v-flex>
      <v-flex v-if="user && user.role === 'TEACHER' && currentUser.id === user.id" xs12>
        <v-btn :to="'/create-template'">Add template</v-btn>
      </v-flex>
      <template-list :items="templates" :actions="isAuthorized ? listActions : []"
                     @action-clicked="onActionClicked"/>
    </v-layout>
  </v-container>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex'
import TemplateList from './base/TemplateList.vue'

export default {
  name: 'Templates',
  components: {TemplateList},
  props: ['userIdStr'],
  data() {
    return {
      listActions: [{name: "Edit", icon: "build"}]
    };
  },
  computed: {
    userId: function() {
      return parseInt(this.userIdStr);
    },
    ...mapState('account', {
      currentUser: state => state.user
    }),
    ...mapState('users', {
      user(state) { 
        const ret = state.items.find(x => x.id === this.userId);
        return ret;
      }
    }),
    templates: function() {
      return this.user.templates;
    },
    completedTemplates: function() {
      return this.userTemplates.filter(t => t.completed);
    },
    uncompletedTemplates: function() {
      return this.userTemplates.filter(t => !t.completed);
    },
    isAuthorized: function() {
      return this.user && this.user.role === 'TEACHER' && this.currentUser.id === this.user.id;
    }
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
    }),
    ...mapActions('templates', {
      deleteTemplate: 'delete'
    }),
    onActionClicked({name, index}) {
      this.$router.push(`/template/${this.templates[index].id}/edit`);
    }
  }
}
</script>
