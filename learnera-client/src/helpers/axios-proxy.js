import axios from 'axios'

const {get, put, post, delete: delete_, ...others} = axios;

export default {
  ...Object.keys(others).map(m => axios[m.name]),
  get(url, options) {
    console.log(`Performing GET to ${url}`);
    return axios.get(url, options);
  },
  put(url, data, options) {
    console.log(`Performing PUT to ${url}`);
    return axios.put(url, data, options);
  },
  post(url, data, options) {
    console.log(`Performing POST to ${url}`);
    return axios.post(url, data, options);
  },
  delete(url, options) {
    console.log(`Performing DELETE to ${url}`);
    return axios.delete(url, options);
  }
}