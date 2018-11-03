import {authHeader, handleResponse} from '../helpers/auth-helper'
import axios from 'axios'

export const userService = {
  login,
  logout,
  register,
  getAll,
  getById,
  update,
  delete: _delete
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

function getAll() {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.get(`/api/users`, requestOptions).then(handleResponse);
}


function getById(id) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.get(`/api/users/${id}`, requestOptions).then(handleResponse);
}

function update(user) {
  const requestOptions = {
    headers: {...authHeader(), 'Content-Type': 'application/json'},
  };

  return axios.put(`/users/${user.id}`, user, requestOptions).then(handleResponse);
}

// prefixed function name with underscore because delete is a reserved word in javascript
function _delete(id) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.delete(`/api/users/${id}`, requestOptions).then(handleResponse);
}
