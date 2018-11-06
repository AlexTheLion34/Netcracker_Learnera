import {authHeader, handleResponse} from '../helpers/auth-helper'
import axios from 'axios'

export default function(apiUrl) {
  return {
    create(item) {
      const requestOptions = {
        headers: {...authHeader(), 'Content-Type': 'application/json'}
      };
    
      console.log(`Performing POST to ${apiUrl}`);
      return axios.post(apiUrl, item, requestOptions).then(handleResponse);
    },
    update(item) {
      const requestOptions = {
        headers: {...authHeader(), 'Content-Type': 'application/json'},
      };
    
      console.log(`Performing PUT to ${apiUrl}/${item.id}`);
      return axios.put(`${apiUrl}/${item.id}`, item, requestOptions).then(handleResponse);
    },
    delete(id) {
      const requestOptions = {
        headers: authHeader()
      };

      console.log(`Performing DELETE to ${apiUrl}/${id}`);
      return axios.delete(`${apiUrl}/${id}`, requestOptions).then(handleResponse);
    }, 
    getById(id) {
      const requestOptions = {
        headers: authHeader()
      };
    
      console.log(`Performing GET to ${apiUrl}/${id}`);
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