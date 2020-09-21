import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IApplicantEducationalInformation } from 'app/shared/model/applicant-educational-information.model';

type EntityResponseType = HttpResponse<IApplicantEducationalInformation>;
type EntityArrayResponseType = HttpResponse<IApplicantEducationalInformation[]>;

@Injectable({ providedIn: 'root' })
export class ApplicantEducationalInformationService {
  public resourceUrl = SERVER_API_URL + 'api/applicant-educational-informations';

  constructor(protected http: HttpClient) {}

  create(applicantEducationalInformation: IApplicantEducationalInformation): Observable<EntityResponseType> {
    return this.http.post<IApplicantEducationalInformation>(this.resourceUrl, applicantEducationalInformation, { observe: 'response' });
  }

  update(applicantEducationalInformation: IApplicantEducationalInformation): Observable<EntityResponseType> {
    return this.http.put<IApplicantEducationalInformation>(this.resourceUrl, applicantEducationalInformation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IApplicantEducationalInformation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IApplicantEducationalInformation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
