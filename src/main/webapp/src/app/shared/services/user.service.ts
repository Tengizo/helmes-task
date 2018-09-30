import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {SERVER_API_URL} from '../../app.constants';
import {HttpClient} from '@angular/common/http';
import {UserAdd} from '../models/user-add';
import {User} from '../models/user';
import {UserUpdate} from '../models/user-update';

/* part of url for sectors */
const USER_URL = '/users';

@Injectable()
export class UserService {

  constructor(private _http: HttpClient) { }

  addUser(user: UserAdd): Observable<User> {
    return this._http.post<User>(SERVER_API_URL + USER_URL + '/add', user)
  }

  update(user: UserUpdate): Observable<User> {
    return this._http.post<User>(SERVER_API_URL + USER_URL + '/update', user)
  }

  getUserByName(name: string): Observable<User> {
    return this._http.get<User>(SERVER_API_URL + USER_URL + '/get/' + name)
  }
}
