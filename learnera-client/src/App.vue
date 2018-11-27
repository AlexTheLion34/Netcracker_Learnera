<template>
  <v-app>
    <v-navigation-drawer 
      v-model="sidebar" 
      app
    >
      <v-list>
        <v-list-tile
          v-for="item in menuItems"
          v-if="!loggedIn && item.isPublic || loggedIn && !item.isPublic"
          :key="item.title"
          :to="item.path"
        >
          <v-list-tile-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>{{ item.title }}</v-list-tile-content>
        </v-list-tile>
      </v-list>
    </v-navigation-drawer>
    <v-toolbar app>
      <span class="hidden-sm-and-up">
        <v-toolbar-side-icon @click="sidebar = !sidebar" />
      </span>
      <v-toolbar-title>
        <router-link 
          to="/" 
          tag="span" 
          style="cursor: pointer"
        >
          {{ appTitle }}
        </router-link>
      </v-toolbar-title>
      <v-spacer/>
      <v-toolbar-items class="hidden-xs-only">
        <v-btn
          v-for="item in menuItems"
          v-if="!loggedIn && item.isPublic || loggedIn && !item.isPublic"
          :key="item.title"
          :to="item.path"
          flat
        >
          <v-icon 
            left 
            dark
          >{{ item.icon }}</v-icon>
          {{ item.title }}
        </v-btn>
      </v-toolbar-items>
    </v-toolbar>

    <v-snackbar v-model="alertShown" :timeout="0" style="margin: 0px" top>
      <v-icon :color="alert.type === 'success' ? 'blue' : 'red darken-3'">
        {{ alert.type === 'success' ? 'info' : 'error' }}
      </v-icon>
      <span style="margin: 0 0 0 1em">{{ alert.message }}</span>
      <v-btn
        color="pink"
        flat
        @click="snackbar = false"
      >
        Close
      </v-btn>
    </v-snackbar>

    <v-content>
      <router-view/>
    </v-content>
  </v-app>
</template>

<script>
import {mapState, mapActions} from 'vuex'

export default { 
  data() {
    return {
      appTitle: "LEARNERA",
      sidebar: false,
      alertShown: false,
      prevTimeout: 0
    };
  },
  computed: {
    ...mapState({
      alert: state => state.alert,
      loggedIn: state => state.account.status.loggedIn,
      user: state => state.account.user
    }),
    userPrefix: function() {
      return `/user/${this.user.id}`;
    },
    menuItems: function() {
      var baseItems = [
          {title: "Home", path: this.loggedIn ? this.userPrefix : '', icon: 'face'},
          {title: 'Logout', path: '/logout', icon: 'accessible_forward'},
          // {title: "Login", path: "/login", icon: "lock_open", isPublic: true},
        ]
      if (this.loggedIn && this.user.role == "TEACHER") {
        baseItems.splice(1, 0, {title: "Students", path: "/create", icon: "edit"})
      } 
      return baseItems  
    },
    alertId: function() {
      return this.alert.id;
    }
  },
  watch: {
    alertId: function(id) {
      let val = this.alert.message;
      if (!val) {
        return;
      }
      this.alertShown = true;

      if (this.prevTimeout) {
        clearTimeout(this.prevTimeout);
      }
      this.prevTimeout = setTimeout(_ => {
        this.clearAlert();
        this.alertShown = false;
      }, 6000);
    }
  },
  methods: {
    ...mapActions({
      clearAlert: 'alert/clear' 
    })
  },
};
</script>
