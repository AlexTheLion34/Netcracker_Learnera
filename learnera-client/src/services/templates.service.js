import {authHeader, handleResponse} from '../helpers/auth-helper'
import axios from '../helpers/axios-proxy'
import crudService from './crud-service'

const apiUrl = '/api/templates'

export const templateService = {
  ...crudService(apiUrl),
  getByTeacherId,
}


function getByTeacherId(id) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.get(`${apiUrl}/teacher/${id}`, requestOptions).then(handleResponse);
}
