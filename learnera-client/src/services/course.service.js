import {authHeader, handleResponse} from '../helpers/auth-helper'
import axios from 'axios'

export const courseService = {
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

  return axios.post('/api/courses', course, requestOptions).then(handleResponse);
}

function getAll() {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.get(`/api/courses`, requestOptions).then(handleResponse);
}


function getById(id) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.get(`/api/courses/${id}`, requestOptions).then(handleResponse);
}

function getByTeacherId(id) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.get(`/api/courses/teacher/${id}`, requestOptions).then(handleResponse);
}

function update(course) {
  const requestOptions = {
    headers: {...authHeader(), 'Content-Type': 'application/json'},
  };

  return axios.put(`/api/courses/${course.id}`, course, requestOptions).then(handleResponse);
}

// prefixed function name with underscore because delete is a reserved word in javascript
function _delete(id) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.delete(`/api/courses/${id}`, requestOptions).then(handleResponse);
}
