<template>
  <v-container>
    <v-layout row>
      <v-flex xs6 mt-5>
        <v-layout column>
          <v-flex xs12 class="text-xs-center">
            <h6 class="title">New Student</h6>
          </v-flex>
          <v-flex mt-2>
            <form @submit.prevent="onSignup">
          <v-layout column>
            <v-flex>
              <v-text-field
                id="name"
                v-model="name"
                name="name"
                label="Name"
                required 
                oninvalid="this.setCustomValidity('Enter name!')"
                oninput="setCustomValidity('')"
                height = 40
                outline
              />
            </v-flex>
            <v-flex>
              <v-text-field
                id="surname"
                v-model="surname"
                name="surname"
                label="Surname"
                required 
                oninvalid="this.setCustomValidity('Enter surname!')"
                oninput="setCustomValidity('')"
                height = 40
                outline
              />
            </v-flex>
            <v-flex>
              <v-text-field
                id="email"
                v-model="email"
                name="email"
                label="E-mail"
                type="email"
                required 
                oninvalid="this.setCustomValidity('Enter email!')"
                oninput="setCustomValidity('')"
                height = 40
                outline
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
                oninvalid="this.setCustomValidity('Enter password!')"
                oninput="setCustomValidity('')"
                height = 40
                outline
              />
            </v-flex>
            <v-flex>
              <v-text-field
                id="confirmPassword"
                v-model="confirmPassword"
                name="confirmPassword"
                label="Confirm Password"
                :rules="[comparePasswords]"
                type="password"
                required 
                oninvalid="this.setCustomValidity('Enter password again!')"
                oninput="setCustomValidity('')"
                height = 40
                outline
              />
            </v-flex>
            <v-flex 
              class="text-xs-center" 
            >
              <v-btn  
                type="submit"
              >Add</v-btn>
            </v-flex>
          </v-layout>
        </form>
          </v-flex>
        </v-layout>
      </v-flex>
      <v-divider class="mx-3" vertical/>
      <v-flex xs6 mt-5>
        <v-layout column="">
          <v-flex xs12 class="text-xs-center">
            <h6 class="title">Students</h6>
          </v-flex>
          <v-flex mt-2>
            <v-card>
              <v-card-title>
                Student
                  <v-spacer></v-spacer>
                    <v-text-field
                      v-model="search"
                      append-icon="search"
                      label="Search"
                      single-line
                      hide-details
                    ></v-text-field>
                </v-card-title>
            </v-card>
            <v-data-table 
              :headers="[
                {text: 'Name', value: 'info.firstName'}, 
                {text: 'Email', value: 'email'},
                
              ]"
              :pagination.sync="pagination"
              :light="true"
              :search="search"
              :items="students"
              hide-actions
              class="elevation-1"
            >
              <template slot="items" slot-scope="props">
                <td>
                  <v-avatar color="teal"  size="36px">
                    <img v-if="props.item.avatar" :src="props.item.avatar">
                    <span v-else class="white--text headline">{{props.item.info.firstName[0]}}</span>
                  </v-avatar>
                  {{props.item.info.firstName + ' ' + props.item.info.lastName}}
                  </td>
                  <td>{{props.item.email}}</td>
              </template>
              <v-alert slot="no-results" :value="true" color="error" icon="warning">
                  Your search for "{{ search }}" found no results.
              </v-alert>
            </v-data-table>
            <div class="text-xs-center pt-2">
              <v-pagination v-model="pagination.page" :length="pages"></v-pagination>
            </div>
          </v-flex>
        </v-layout>
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
      search: '',
      pagination: {
        rowsPerPage: 5,
      },
      name: '',
      surname: '',
      email: '',
      password: '',
      confirmPassword: '',
      created: Boolean,
    }
  },
  computed: {
    comparePasswords() {
      return this.password !== this.confirmPassword ? 'Passwords do not match' : true
    },
    ...mapState('account', {
      currentUser: state => state.user
    }),
    ...mapState('users', {
      students: function(state) {
        const ret = state.items.filter(s => s.role === 'STUDENT')
        return ret
    },
    }),
    pages () {
      return Math.ceil(this.students.length / this.pagination.rowsPerPage)
    }
  },
  watch: {
    created: function(val) {
      if (val) {
        this.getAllStudents();
      }
      this.created = false
    }
  },
  beforeMount() {
    console.log(this.pagination.totalItems)
    this.getAllStudents()
    this.created = false
  },
  methods: {
    ...mapActions('account', ['create']),
    ...mapActions('users', {
      getAllStudents: 'getAllStudents',
    }),
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
      this.created = true
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
