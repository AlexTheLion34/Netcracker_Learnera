import {authHeader, handleResponse} from '../helpers/auth-helper'
import axios from 'axios'

export default function(apiUrl) {
  return {
    create(item) {
      const requestOptions = {
        headers: {...authHeader(), 'Content-Type': 'application/json'}
      };
    
      return axios.post(apiUrl, item, requestOptions).then(handleResponse);
    },
    update(item) {
      const requestOptions = {
        headers: {...authHeader(), 'Content-Type': 'application/json'},
      };
    
      return axios.put(`${apiUrl}/${item.id}`, item, requestOptions).then(handleResponse);
    },
    delete(id) {
      const requestOptions = {
        headers: authHeader()
      };
    
      return axios.delete(`${apiUrl}/${id}`, requestOptions).then(handleResponse);
    }, 
    getById(id) {
      const requestOptions = {
        headers: authHeader()
      };
    
      return axios.get(`${apiUrl}/${id}`, requestOptions).then(handleResponse);
    },
    getAll() {
      const requestOptions = {
        headers: authHeader()
      };
    
      return axios.get(apiUrl, requestOptions).then(handleResponse);
    }  
  }
}