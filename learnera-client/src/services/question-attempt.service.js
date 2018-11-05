import {authHeader, handleResponse} from '../helpers/auth-helper'
import axios from 'axios'
import crudService from './crud-service'

const apiUrl = '/api/question-attempts'

export const questionAttemptService = {
  ...crudService(apiUrl),
  getByQuestionId,
}

function getByQuestionId(id) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.get(`${apiUrl}/question/${id}`, requestOptions).then(handleResponse);
}
