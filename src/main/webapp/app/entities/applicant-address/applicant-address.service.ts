import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IApplicantAddress } from 'app/shared/model/applicant-address.model';

type EntityResponseType = HttpResponse<IApplicantAddress>;
type EntityArrayResponseType = HttpResponse<IApplicantAddress[]>;

@Injectable({ providedIn: 'root' })
export class ApplicantAddressService {
  public resourceUrl = SERVER_API_URL + 'api/applicant-addresses';

  constructor(protected http: HttpClient) {}

  create(applicantAddress: IApplicantAddress): Observable<EntityResponseType> {
    return this.http.post<IApplicantAddress>(this.resourceUrl, applicantAddress, { observe: 'response' });
  }

  update(applicantAddress: IApplicantAddress): Observable<EntityResponseType> {
    return this.http.put<IApplicantAddress>(this.resourceUrl, applicantAddress, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IApplicantAddress>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IApplicantAddress[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
