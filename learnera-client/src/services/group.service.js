import {authHeader, handleResponse} from '../helpers/auth-helper'
import axios from 'axios'
import crudService from './crud-service'

const apiUrl = '/api/groups'

export const groupService = {
  ...crudService(apiUrl),
  getByCuratorId,
}

function getByCuratorId(id) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.get(`${apiUrl}/curator/${id}`, requestOptions).then(handleResponse);
}
