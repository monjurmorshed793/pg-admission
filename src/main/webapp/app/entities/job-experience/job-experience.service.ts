import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJobExperience } from 'app/shared/model/job-experience.model';

type EntityResponseType = HttpResponse<IJobExperience>;
type EntityArrayResponseType = HttpResponse<IJobExperience[]>;

@Injectable({ providedIn: 'root' })
export class JobExperienceService {
  public resourceUrl = SERVER_API_URL + 'api/job-experiences';

  constructor(protected http: HttpClient) {}

  create(jobExperience: IJobExperience): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobExperience);
    return this.http
      .post<IJobExperience>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(jobExperience: IJobExperience): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobExperience);
    return this.http
      .put<IJobExperience>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IJobExperience>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IJobExperience[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(jobExperience: IJobExperience): IJobExperience {
    const copy: IJobExperience = Object.assign({}, jobExperience, {
      fromDate: jobExperience.fromDate && jobExperience.fromDate.isValid() ? jobExperience.fromDate.format(DATE_FORMAT) : undefined,
      toDate: jobExperience.toDate && jobExperience.toDate.isValid() ? jobExperience.toDate.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((jobExperience: IJobExperience) => {
        jobExperience.fromDate = jobExperience.fromDate ? moment(jobExperience.fromDate) : undefined;
        jobExperience.toDate = jobExperience.toDate ? moment(jobExperience.toDate) : undefined;
      });
    }
    return res;
  }
}
