import {authHeader, handleResponse} from '../helpers/auth-helper'
import axios from '../helpers/axios-proxy'
import crudService from './crud-service'

export const messageService = function(chatApi) {
  return {
    ...crudService(chatApi),
    getByDestinationId(id) {
      const requestOptions = {
        headers: authHeader()
      }

      return axios.get(`${chatApi}/destination/${id}`, requestOptions).then(handleResponse);
    }
  };
}
