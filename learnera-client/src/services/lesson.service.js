import {authHeader, handleResponse} from '../helpers/auth-helper'
import axios from '../helpers/axios-proxy'
import crudService from './crud-service'

const apiUrl = '/api/lessons'

export const lessonService = {
  ...crudService(apiUrl),
  getByModuleId,
}

function getByModuleId(id) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.get(`${apiUrl}/module/${id}`, requestOptions).then(handleResponse);
}
