import { authHeader } from '../helpers';
import axios from 'axios'

export const userService = {
    login,
    logout,
    register,
    getAll,
    getById,
    update,
    delete: _delete
};

function login(email, password) {
    var user = {email, password};

    return axios.get(`/api/auth`, {
        headers: { 'Authorization': 'Basic ' + btoa(email + ':' + password) }
      }).then(handleResponse)
        .then(data => {
            localStorage.setItem('user', data);
            return data;
        })
        .catch(error => {
            this.errors.push(error);
        });
}

function logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('user');
}

function register(user) {
    return axios.post('/api/auth', user, {
        headers: {
          'Content-Type': 'application/json'
        }
      }).then(handleResponse);
}

function getAll() {
    const requestOptions = {
        headers: authHeader()
    };

    return axios.get(`/users`, requestOptions).then(handleResponse);
}


function getById(id) {
    const requestOptions = {
        headers: authHeader()
    };

    return axios.get(`/users/${id}`, requestOptions).then(handleResponse);
}

function update(user) {
    const requestOptions = {
        headers: { ...authHeader(), 'Content-Type': 'application/json' },
    };

    return axios.put(`/users/${user.id}`, user, requestOptions).then(handleResponse);
}

// prefixed function name with underscore because delete is a reserved word in javascript
function _delete(id) {
    const requestOptions = {
        headers: authHeader()
    };

    return axios.delete(`/users/${id}`, requestOptions).then(handleResponse);
}

function handleResponse(response) {
    if (response.status != 200) {
        if (response.status === 401) {
            // auto logout if 401 response returned from api
            logout();
            location.reload(true);
        }

        const error = (data && data.message) || response.statusText;
        return Promise.reject(error);
    }

    return response.data;
}
