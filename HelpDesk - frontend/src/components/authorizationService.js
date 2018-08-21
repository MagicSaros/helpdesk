const authenticationDataLocalStorageKeys = {
	user: 'User',
	header: 'Authentication token header',
	string: 'Authentication token string'
};

class AuthorizationService {

    static getCurrentUser() {
        let user = JSON.parse(localStorage.getItem(authenticationDataLocalStorageKeys.user));
        return user;
    }

    static setCurrentUser(user) {
        localStorage.setItem(authenticationDataLocalStorageKeys.user, JSON.stringify(user));
    }

    static deleteCurrentUser() {
        localStorage.removeItem(authenticationDataLocalStorageKeys.user);
    }

    static getAuthorizationToken() {
        let header = localStorage.getItem(authenticationDataLocalStorageKeys.header);
        let string = localStorage.getItem(authenticationDataLocalStorageKeys.string);
        return {
            header: header,
            string: string
        };
    }
    
    static setAuthorizationToken(token) {
        localStorage.setItem(authenticationDataLocalStorageKeys.header, token.header);
        localStorage.setItem(authenticationDataLocalStorageKeys.string, token.string);
    }

    static deleteAuthorizationToken() {
        localStorage.removeItem(authenticationDataLocalStorageKeys.header);
        localStorage.removeItem(authenticationDataLocalStorageKeys.string);
    }
}

export default AuthorizationService;