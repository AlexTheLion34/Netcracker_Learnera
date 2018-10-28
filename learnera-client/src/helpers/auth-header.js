export function authHeader() {
    // return authorization header with jwt token
    let user = JSON.parse(localStorage.getItem('user'));

    if (user) {
        return { 'Authorization': 'Basic ' + btoa(user.email + ':' + user.password)};
    } else {
        return {};
    }
}