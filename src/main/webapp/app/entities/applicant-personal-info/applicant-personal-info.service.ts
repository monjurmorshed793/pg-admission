import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IApplicantPersonalInfo } from 'app/shared/model/applicant-personal-info.model';

type EntityResponseType = HttpResponse<IApplicantPersonalInfo>;
type EntityArrayResponseType = HttpResponse<IApplicantPersonalInfo[]>;

@Injectable({ providedIn: 'root' })
export class ApplicantPersonalInfoService {
  public resourceUrl = SERVER_API_URL + 'api/applicant-personal-infos';

  constructor(protected http: HttpClient) {}

  create(applicantPersonalInfo: IApplicantPersonalInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applicantPersonalInfo);
    return this.http
      .post<IApplicantPersonalInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(applicantPersonalInfo: IApplicantPersonalInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applicantPersonalInfo);
    return this.http
      .put<IApplicantPersonalInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IApplicantPersonalInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IApplicantPersonalInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(applicantPersonalInfo: IApplicantPersonalInfo): IApplicantPersonalInfo {
    const copy: IApplicantPersonalInfo = Object.assign({}, applicantPersonalInfo, {
      dateOfBirth:
        applicantPersonalInfo.dateOfBirth && applicantPersonalInfo.dateOfBirth.isValid()
          ? applicantPersonalInfo.dateOfBirth.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOfBirth = res.body.dateOfBirth ? moment(res.body.dateOfBirth) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((applicantPersonalInfo: IApplicantPersonalInfo) => {
        applicantPersonalInfo.dateOfBirth = applicantPersonalInfo.dateOfBirth ? moment(applicantPersonalInfo.dateOfBirth) : undefined;
      });
    }
    return res;
  }
}
