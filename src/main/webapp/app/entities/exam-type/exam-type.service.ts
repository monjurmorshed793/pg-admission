import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IExamType } from 'app/shared/model/exam-type.model';

type EntityResponseType = HttpResponse<IExamType>;
type EntityArrayResponseType = HttpResponse<IExamType[]>;

@Injectable({ providedIn: 'root' })
export class ExamTypeService {
  public resourceUrl = SERVER_API_URL + 'api/exam-types';

  constructor(protected http: HttpClient) {}

  create(examType: IExamType): Observable<EntityResponseType> {
    return this.http.post<IExamType>(this.resourceUrl, examType, { observe: 'response' });
  }

  update(examType: IExamType): Observable<EntityResponseType> {
    return this.http.put<IExamType>(this.resourceUrl, examType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IExamType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IExamType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
