import {router} from '../router'

export function authHeader() 
{
  // return authorization header with jwt token
  let user = JSON.parse(localStorage.getItem('user'));
    
  if (user) {
    return {'Authorization': 'Basic ' + btoa(user.email + ':' + user.password)};
  } else {
    return {};
  }
}

export function handleResponse(response) {
  if (response.status != 200) {
    if (response.status === 401) {
      // auto logout if 401 response returned from api
      router.push('/login');
    }
    const error = (response.data && response.data.message) || response.statusText;
    return Promise.reject(error);
  }
  
  return response.data;
}