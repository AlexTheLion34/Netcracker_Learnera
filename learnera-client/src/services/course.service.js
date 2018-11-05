import {authHeader, handleResponse} from '../helpers/auth-helper'
import axios from 'axios'
import crudService from './crud-service'

const apiUrl = '/api/courses'

export const courseService = {
  ...crudService(apiUrl),
  getByTeacherId,
}

function getByTeacherId(id) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.get(`${apiUrl}/teacher/${id}`, requestOptions).then(handleResponse);
}
