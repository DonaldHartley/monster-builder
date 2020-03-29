import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMonster } from 'app/shared/model/monster.model';

type EntityResponseType = HttpResponse<IMonster>;
type EntityArrayResponseType = HttpResponse<IMonster[]>;

@Injectable({ providedIn: 'root' })
export class MonsterService {
  public resourceUrl = SERVER_API_URL + 'api/monsters';

  constructor(protected http: HttpClient) {}

  create(monster: IMonster): Observable<EntityResponseType> {
    return this.http.post<IMonster>(this.resourceUrl, monster, { observe: 'response' });
  }

  update(monster: IMonster): Observable<EntityResponseType> {
    return this.http.put<IMonster>(this.resourceUrl, monster, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMonster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMonster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
