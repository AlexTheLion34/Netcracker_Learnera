import {authHeader, handleResponse} from '../helpers/auth-helper'
import axios from '../helpers/axios-proxy'
import crudService from './crud-service'

const apiUrl = '/api/lessons'

export const lessonService = {
  ...crudService(apiUrl),
  getByWeekId,
}

function getByWeekId(id) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.get(`${apiUrl}/week/${id}`, requestOptions).then(handleResponse);
}
