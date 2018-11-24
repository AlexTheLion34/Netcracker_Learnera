import {authHeader, handleResponse} from '../helpers/auth-helper'
import axios from '../helpers/axios-proxy'
import crudService from './crud-service'

const apiUrl = '/api/question-attempts'

export const questionAttemptService = {
  ...crudService(apiUrl),
  getByQuestionId,
  scoreAttempts,
  findLatestUserWeekAttempts,
  findAllCourseAttempts
}

function getByQuestionId(id) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.get(`${apiUrl}/question/${id}`, requestOptions).then(handleResponse);
}

function scoreAttempts(attempts) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.post(`${apiUrl}/batch`, attempts, requestOptions).then(handleResponse);
}

function findLatestUserWeekAttempts(userId, weekId) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.get(`${apiUrl}/user/${userId}/week/${weekId}/latest`, requestOptions).then(handleResponse);
}

function findAllCourseAttempts(courseId) {
  const requestOptions = {
    headers: authHeader()
  };

  return axios.get(`${apiUrl}/course/${courseId}`, requestOptions).then(handleResponse);
}
