import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IApplicant } from 'app/shared/model/applicant.model';

type EntityResponseType = HttpResponse<IApplicant>;
type EntityArrayResponseType = HttpResponse<IApplicant[]>;

@Injectable({ providedIn: 'root' })
export class ApplicantService {
  public resourceUrl = SERVER_API_URL + 'api/applicants';

  constructor(protected http: HttpClient) {}

  create(applicant: IApplicant): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applicant);
    return this.http
      .post<IApplicant>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(applicant: IApplicant): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applicant);
    return this.http
      .put<IApplicant>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IApplicant>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IApplicant[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(applicant: IApplicant): IApplicant {
    const copy: IApplicant = Object.assign({}, applicant, {
      appliedOn: applicant.appliedOn && applicant.appliedOn.isValid() ? applicant.appliedOn.toJSON() : undefined,
      applicationFeePaidOn:
        applicant.applicationFeePaidOn && applicant.applicationFeePaidOn.isValid() ? applicant.applicationFeePaidOn.toJSON() : undefined,
      selectedRejectedOn:
        applicant.selectedRejectedOn && applicant.selectedRejectedOn.isValid() ? applicant.selectedRejectedOn.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.appliedOn = res.body.appliedOn ? moment(res.body.appliedOn) : undefined;
      res.body.applicationFeePaidOn = res.body.applicationFeePaidOn ? moment(res.body.applicationFeePaidOn) : undefined;
      res.body.selectedRejectedOn = res.body.selectedRejectedOn ? moment(res.body.selectedRejectedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((applicant: IApplicant) => {
        applicant.appliedOn = applicant.appliedOn ? moment(applicant.appliedOn) : undefined;
        applicant.applicationFeePaidOn = applicant.applicationFeePaidOn ? moment(applicant.applicationFeePaidOn) : undefined;
        applicant.selectedRejectedOn = applicant.selectedRejectedOn ? moment(applicant.selectedRejectedOn) : undefined;
      });
    }
    return res;
  }
}
