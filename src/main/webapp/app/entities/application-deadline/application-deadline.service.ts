import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IApplicationDeadline } from 'app/shared/model/application-deadline.model';

type EntityResponseType = HttpResponse<IApplicationDeadline>;
type EntityArrayResponseType = HttpResponse<IApplicationDeadline[]>;

@Injectable({ providedIn: 'root' })
export class ApplicationDeadlineService {
  public resourceUrl = SERVER_API_URL + 'api/application-deadlines';

  constructor(protected http: HttpClient) {}

  create(applicationDeadline: IApplicationDeadline): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applicationDeadline);
    return this.http
      .post<IApplicationDeadline>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(applicationDeadline: IApplicationDeadline): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applicationDeadline);
    return this.http
      .put<IApplicationDeadline>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IApplicationDeadline>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IApplicationDeadline[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(applicationDeadline: IApplicationDeadline): IApplicationDeadline {
    const copy: IApplicationDeadline = Object.assign({}, applicationDeadline, {
      fromDate: applicationDeadline.fromDate && applicationDeadline.fromDate.isValid() ? applicationDeadline.fromDate.toJSON() : undefined,
      toDate: applicationDeadline.toDate && applicationDeadline.toDate.isValid() ? applicationDeadline.toDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fromDate = res.body.fromDate ? moment(res.body.fromDate) : undefined;
      res.body.toDate = res.body.toDate ? moment(res.body.toDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((applicationDeadline: IApplicationDeadline) => {
        applicationDeadline.fromDate = applicationDeadline.fromDate ? moment(applicationDeadline.fromDate) : undefined;
        applicationDeadline.toDate = applicationDeadline.toDate ? moment(applicationDeadline.toDate) : undefined;
      });
    }
    return res;
  }
}
