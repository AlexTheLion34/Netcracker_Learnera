import {authHeader, handleResponse} from '../helpers/auth-helper'
import axios from 'axios'
import crudService from './crud-service'

const apiUrl = '/api/questions'

export const lessonService = {
  ...crudService(apiUrl),
}
