import AXIOS from './axios-base'

const {get, put, post, delete: delete_, ...others} = AXIOS;

export default {
  ...Object.keys(others).map(m => AXIOS[m.name]),
  get(url, options) {
    console.log(`Performing GET to ${url}`);
    return AXIOS.get(url, options);
  },
  put(url, data, options) {
    console.log(`Performing PUT to ${url}`);
    return AXIOS.put(url, data, options);
  },
  post(url, data, options) {
    console.log(`Performing POST to ${url}`);
    return AXIOS.post(url, data, options);
  },
  delete(url, options) {
    console.log(`Performing DELETE to ${url}`);
    return AXIOS.delete(url, options);
  }
}