import {authHeader, handleResponse} from '../helpers/auth-helper'
import axios from 'axios'
import {userService} from './user.service'

export const groupService = {
  create,
  getAll,
  getById,
  update,
  delete: _delete
}

function create(group) {
  const requestOptions = {
    headers: {...authHeader(), 'Content-Type': 'application/json'}
  };

  return axios.post('/api/groups', group, requestOptions).then(handleResponse);
}

function getAll() {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.get(`/api/groups`, requestOptions).then(handleResponse);
}


function getById(id) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.get(`/api/groups/${id}`, requestOptions).then(handleResponse);
}

function update(course) {
  const requestOptions = {
    headers: {...authHeader(), 'Content-Type': 'application/json'},
  };

  return axios.put(`/api/groups/${course.id}`, course, requestOptions).then(handleResponse);
}

// prefixed function name with underscore because delete is a reserved word in javascript
function _delete(id) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.delete(`/api/groups/${id}`, requestOptions).then(handleResponse);
}
