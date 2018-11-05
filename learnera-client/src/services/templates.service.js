import {authHeader, handleResponse} from '../helpers/auth-helper'
import axios from 'axios'

export const templateService = {
  create,
  getAll,
  getById,
  getByTeacherId,
  update,
  delete: _delete
}

function create(course) {
  const requestOptions = {
    headers: {...authHeader(), 'Content-Type': 'application/json'}
  };

  return axios.post('/api/templates', course, requestOptions).then(handleResponse);
}

function getAll() {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.get(`/api/templates`, requestOptions).then(handleResponse);
}


function getById(id) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.get(`/api/templates/${id}`, requestOptions).then(handleResponse);
}

function getByTeacherId(id) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.get(`/api/templates/teacher/${id}`, requestOptions).then(handleResponse);
}

function update(template) {
  const requestOptions = {
    headers: {...authHeader(), 'Content-Type': 'application/json'},
  };

  return axios.put(`/api/templates/${template.id}`, template, requestOptions).then(handleResponse);
}

// prefixed function name with underscore because delete is a reserved word in javascript
function _delete(id) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.delete(`/api/templates/${id}`, requestOptions).then(handleResponse);
}
