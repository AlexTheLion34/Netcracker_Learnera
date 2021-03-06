import {authHeader, handleResponse} from '../helpers/auth-helper'
import axios from '../helpers/axios-proxy'
import crudService from './crud-service'

const apiUrl = '/api/groups'

export const groupService = {
  ...crudService(apiUrl),
  getByCuratorId,
  getByCourseId
}

function getByCuratorId(id) {
  const requestOptions = {
    headers: authHeader()
  };

  console.log(`Performing GET to ${apiUrl}/curator/${id}`);
  return axios.get(`${apiUrl}/curator/${id}`, requestOptions).then(handleResponse);
}

function getByCourseId(id) {
  const requestOptions = {
    headers: authHeader()
  };

  console.log(`Performing GET to ${apiUrl}/course/${id}`);
  return axios.get(`${apiUrl}/course/${id}`, requestOptions).then(handleResponse);
}