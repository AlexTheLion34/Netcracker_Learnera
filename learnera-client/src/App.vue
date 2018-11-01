<template>
  <v-app>
    <v-navigation-drawer v-model="sidebar" app>
      <v-list>
        <v-list-tile
          v-for="item in menuItems"
          :key="item.title"
          :to="item.path">
          <v-list-tile-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>{{ item.title }}</v-list-tile-content>
        </v-list-tile>
      </v-list>
    </v-navigation-drawer>

    <v-toolbar app>
      <span class="hidden-sm-and-up">
        <v-toolbar-side-icon @click="sidebar = !sidebar">
        </v-toolbar-side-icon>
      </span>
      <v-toolbar-title>
        <router-link to="/" tag="span" style="cursor: pointer">
          {{ appTitle }}
        </router-link>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items class="hidden-xs-only">
        <v-btn
          flat
          v-if="!loggedIn && item.isPublic || loggedIn && !item.isPublic"
          v-for="item in menuItems"
          :key="item.title"
          :to="item.path">
          <v-icon left dark>{{ item.icon }}</v-icon>
          {{ item.title }}
        </v-btn>
      </v-toolbar-items>
    </v-toolbar>

    <v-content>
      <router-view></router-view>
    </v-content>

  </v-app>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  data() {
    return {
      appTitle: "ОБУЧАЛОЧКА",
      sidebar: false,
      menuItems: [
        { title: "Profile", path: "/profile", icon: "face" },
        { title: "Logout", path: "/logout", icon: 'accessible_forward' },
        { title: "Registration", path: "/register", icon: "account_circle", isPublic: true },
        { title: "Login", path: "/login", icon: "lock_open", isPublic: true }
      ]
    };
  },
  computed: {
    ...mapState({
        alert: state => state.alert,
        loggedIn: state => state.account.status.loggedIn
    })
  },
  methods: {
      ...mapActions({
          clearAlert: 'alert/clear' 
      })
  },
};
</script>
