import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IApplicantAddress, ApplicantAddress } from 'app/shared/model/applicant-address.model';
import { ApplicantAddressService } from './applicant-address.service';
import { ApplicantAddressComponent } from './applicant-address.component';
import { ApplicantAddressDetailComponent } from './applicant-address-detail.component';
import { ApplicantAddressUpdateComponent } from './applicant-address-update.component';

@Injectable({ providedIn: 'root' })
export class ApplicantAddressResolve implements Resolve<IApplicantAddress> {
  constructor(private service: ApplicantAddressService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IApplicantAddress> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((applicantAddress: HttpResponse<ApplicantAddress>) => {
          if (applicantAddress.body) {
            return of(applicantAddress.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ApplicantAddress());
  }
}

export const applicantAddressRoute: Routes = [
  {
    path: '',
    component: ApplicantAddressComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ApplicantAddresses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ApplicantAddressDetailComponent,
    resolve: {
      applicantAddress: ApplicantAddressResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ApplicantAddresses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ApplicantAddressUpdateComponent,
    resolve: {
      applicantAddress: ApplicantAddressResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ApplicantAddresses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ApplicantAddressUpdateComponent,
    resolve: {
      applicantAddress: ApplicantAddressResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ApplicantAddresses',
    },
    canActivate: [UserRouteAccessService],
  },
];
