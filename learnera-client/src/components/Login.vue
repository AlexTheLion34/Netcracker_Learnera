<template>
  <v-container fluid>
    <v-layout 
      row 
      wrap
    >
      <v-flex 
        xs12 
        class="text-xs-center" 
        mt-5
      >
        <h1>Login</h1>
      </v-flex>
      <v-flex 
        xs12 
        sm6 
        offset-sm3 
        mt-3
      >
        <form @submit.prevent="onLogin">
          <v-layout column>
            <v-flex>
              <v-text-field
                id="email"
                v-model="email"
                name="email"
                label="E-mail"
                type="email"
                required
              />
            </v-flex>
            <v-flex>
              <v-text-field
                id="password"
                v-model="password"
                name="password"
                label="Password"
                type="password"
                required
              />
            </v-flex>
            <v-flex 
              class="text-xs-center" 
              mt-5
            >
              <v-btn 
                color="primary" 
                type="submit"
              >Login</v-btn>
            </v-flex>
          </v-layout>
        </form>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import {mapActions} from 'vuex'

export default {
  name: 'Login',
  data() {
    return {
      email: '',
      password: ''
    }
  },
  created() {
    // reset login status
    this.logout();
  },
  methods: {
    ...mapActions('account', ['login', 'logout']),
    onLogin() {
      const {email, password} = this;
      if (email && password) {
        this.login({email, password});
      }
    }
  }
}
</script>

<style scoped>

</style>
