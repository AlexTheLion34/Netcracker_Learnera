import {authHeader, handleResponse} from '../helpers/auth-helper'
import axios from 'axios'
import crudService from './crud-service'

const apiUrl = '/api/users'

export const userService = {
  login,
  logout,
  register,
  ...crudService(apiUrl)
};

function login(email, password) {
  return axios.get(`/api/auth`, {
    headers: {'Authorization': 'Basic ' + btoa(email + ':' + password)}
  }).then(handleResponse)
    .then(data => {
      localStorage.setItem('user', JSON.stringify(data));
      return data;
    })
    .catch(error => {
      this.errors.push(error);
    });
}

function logout() {
  // remove user from local storage to log user out
  localStorage.removeItem('user');
}

function register(user) {
  return axios.post('/api/auth', user, {
    headers: {
      'Content-Type': 'application/json'
    }
  }).then(handleResponse);
}
