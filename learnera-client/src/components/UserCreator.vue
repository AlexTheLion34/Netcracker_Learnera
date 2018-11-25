<template>
  <v-container fluid>
    <v-layout 
      row 
      wrap
    >
    <v-alert
      v-model="alert"
      dismissible
      :outline="true"
      type="success"
      transition="scale-transition"
    >
      Successfully added a new student.
    </v-alert>
      <v-flex 
        xs12 
        class="text-xs-center" 
        mt-5
      >
        <h1>New Student</h1>
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
              >Add</v-btn>
            </v-flex>
          </v-layout>
        </form>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import {mapActions, mapState} from 'vuex'

export default {
  name: 'UserCreator',
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
      ],
      alert: false
    }
  },
  computed: {
    comparePasswords() {
      return this.password !== this.confirmPassword ? 'Passwords do not match' : true
    },
    ...mapState('account', {
      currentUser: state => state.user
    }),
  },
  methods: {
    ...mapActions('account', ['create']),
    onSignup() {
      const user = {
        email: this.email,
        password: this.password,
        role: 'STUDENT',
        info: {
          lastName: this.surname,
          firstName: this.name
        }
      }
      this.create(user, this.currentUser.id);
      this.alert = true
      this.name = ""
      this.surname = ""
      this.email = ""
      this.password = ""
      this.confirmPassword = ""
    }
  }
}
</script>

<style scoped>

</style>
