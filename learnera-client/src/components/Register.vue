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
        <h1>Registration</h1>
      </v-flex>
      <v-flex 
        xs12 
        sm6
        offset-sm3 
        mt-3
      >
        <form @submit.prevent="onSignup">
          <v-layout column>
            <v-flex>
              <v-text-field
                id="name"
                v-model="name"
                :rules="nameRules"
                name="name"
                label="Name"
                required
              />
            </v-flex>
            <v-flex>
              <v-text-field
                id="surname"
                v-model="surname"
                :rules="nameRules"
                name="surname"
                label="Surname"
                required
              />
            </v-flex>
            <v-flex>
              <v-text-field
                id="email"
                v-model="email"
                :rules="emailRules"
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
            <v-flex>
              <v-text-field
                id="confirmPassword"
                v-model="confirmPassword"
                :rules="[comparePasswords]"
                name="confirmPassword"
                label="Confirm Password"
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
              >Sign up</v-btn>
              <v-btn 
                :to="'/login'"
              >Back</v-btn>
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
  name: 'Register',
  data() {
    return {
      name: '',
      surname: '',
      email: '',
      password: '',
      confirmPassword: '',
      nameRules: [
        v => !!v || 'Name is required',
        v => !!v && v.length <= 20 || 'Name must be less than 30 characters'
      ],
      emailRules: [
        v => !!v || 'Email is required',
        v => !!v && v.length <= 50 || 'Email must be less than 50 characters'
      ]
    }
  },
  computed: {
    comparePasswords() {
      return this.password !== this.confirmPassword ? 'Passwords do not match' : true
    }
  },
  methods: {
    ...mapActions('account', ['register']),
    onSignup() {
      const user = {
        email: this.email,
        password: this.password,
        role: 'TEACHER',
        info: {
          lastName: this.surname,
          firstName: this.name
        }
      }
      this.register(user);
    }
  }
}
</script>

<style scoped>

</style>
