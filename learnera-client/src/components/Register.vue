<template>
  <v-container fluid>
    <v-layout row wrap>
      <v-flex xs12 class="text-xs-center" mt-5>
        <h1>Registration</h1>
      </v-flex>
      <v-flex xs12 sm6 offset-sm3 mt-3>
        <form @submit.prevent="onSignup">
          <v-layout column>
            <v-flex>
              <v-text-field
                name="name"
                label="Name"
                id="name"
                required
                v-model="name"></v-text-field>
            </v-flex>
            <v-flex>
              <v-text-field
                name="surname"
                label="Surname"
                id="surname"
                required
                v-model="surname"></v-text-field>
            </v-flex>
            <v-flex>
              <v-text-field
                name="email"
                label="E-mail"
                id="email"
                type="email"
                required
                v-model="email"></v-text-field>
            </v-flex>
            <v-flex>
              <v-text-field
                name="password"
                label="Password"
                id="password"
                type="password"
                required
                v-model="password"></v-text-field>
            </v-flex>
            <v-flex>
              <v-text-field
                name="confirmPassword"
                label="Confirm Password"
                id="confirmPassword"
                type="password"
                required
                v-model="confirmPassword"
                :rules="[comparePasswords]"
              ></v-text-field>
            </v-flex>
            <v-flex class="text-xs-center" mt-5>
              <v-btn color="primary" type="submit">Sign up</v-btn>
            </v-flex>
          </v-layout>
        </form>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'Register',
    data () {
      return {
        name: '',
        surname: '',
        email: '',
        password: '',
        confirmPassword: ''
      }
    },
    computed: {
      comparePasswords () {
        return this.password !== this.confirmPassword ? 'Passwords do not match' : true
      }
    },
    methods: {
      onSignup () {
        axios.post('http://localhost:9090/signup', {email: this.email, password: this.password}, {
          headers: {
            'Content-Type': 'application/json'
          }
        })
          .then(function (response) {
            console.log(response)
          })
          .catch(function (error) {
            console.log(error)
          })
      }
    }
  }
</script>

<style scoped>

</style>
