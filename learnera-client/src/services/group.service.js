import {authHeader, handleResponse} from '../helpers/auth-helper'
import axios from 'axios'

export const groupService = {
  create,
  getAll,
  getById,
  getByCuratorId,
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

function getByCuratorId(id) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.get(`/api/groups/curator/${id}`, requestOptions).then(handleResponse);
}

function update(group) {
  const requestOptions = {
    headers: {...authHeader(), 'Content-Type': 'application/json'},
  };

  return axios.put(`/api/groups/${group.id}`, group, requestOptions).then(handleResponse);
}

// prefixed function name with underscore because delete is a reserved word in javascript
function _delete(id) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.delete(`/api/groups/${id}`, requestOptions).then(handleResponse);
}
