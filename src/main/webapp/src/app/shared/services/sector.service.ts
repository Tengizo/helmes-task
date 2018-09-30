import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Sector} from '../models/sector';
import {Observable} from 'rxjs';
import {SERVER_API_URL} from '../../app.constants';

/* part of url for sectors */
const SECTOR_URL = '/sectors';

@Injectable()
export class SectorService {

  constructor(private _http: HttpClient) { }

  getSectors(): Observable<Array<Sector>> {
    return this._http.get<Array<Sector>>(SERVER_API_URL + SECTOR_URL + '/get/all')
  }

}
