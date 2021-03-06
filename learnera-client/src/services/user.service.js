import {authHeader, handleResponse} from '../helpers/auth-helper'
import axios from '../helpers/axios-proxy'
import crudService from './crud-service'

const apiUrl = '/api/users'

export const userService = {
  login,
  logout,
  register,
  getByStudyGroupId,
  getByCourseId,
  getAllStudents,
  ...crudService(apiUrl)
};

function getByStudyGroupId(id) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.get(`${apiUrl}/study-group/${id}`, requestOptions).then(handleResponse);
}

function getByCourseId(id) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.get(`${apiUrl}/course/${id}`, requestOptions).then(handleResponse);
}

function getAllStudents() {
  const requestOptions = {
    headers: authHeader()
  };
  return axios.get(`${apiUrl}/students`, requestOptions).then(handleResponse);
}

function login(email, password) {
  return axios.get(`/api/auth`, {
    headers: {'Authorization': 'Basic ' + btoa(email + ':' + password)}
  }).then(handleResponse)
    .then(data => {
      data.password = password;
      localStorage.setItem('user', JSON.stringify(data));
      return data;
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
