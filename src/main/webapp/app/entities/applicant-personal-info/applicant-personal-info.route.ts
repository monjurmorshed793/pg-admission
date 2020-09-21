import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IApplicantPersonalInfo, ApplicantPersonalInfo } from 'app/shared/model/applicant-personal-info.model';
import { ApplicantPersonalInfoService } from './applicant-personal-info.service';
import { ApplicantPersonalInfoComponent } from './applicant-personal-info.component';
import { ApplicantPersonalInfoDetailComponent } from './applicant-personal-info-detail.component';
import { ApplicantPersonalInfoUpdateComponent } from './applicant-personal-info-update.component';

@Injectable({ providedIn: 'root' })
export class ApplicantPersonalInfoResolve implements Resolve<IApplicantPersonalInfo> {
  constructor(private service: ApplicantPersonalInfoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IApplicantPersonalInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((applicantPersonalInfo: HttpResponse<ApplicantPersonalInfo>) => {
          if (applicantPersonalInfo.body) {
            return of(applicantPersonalInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ApplicantPersonalInfo());
  }
}

export const applicantPersonalInfoRoute: Routes = [
  {
    path: '',
    component: ApplicantPersonalInfoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ApplicantPersonalInfos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ApplicantPersonalInfoDetailComponent,
    resolve: {
      applicantPersonalInfo: ApplicantPersonalInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ApplicantPersonalInfos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ApplicantPersonalInfoUpdateComponent,
    resolve: {
      applicantPersonalInfo: ApplicantPersonalInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ApplicantPersonalInfos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ApplicantPersonalInfoUpdateComponent,
    resolve: {
      applicantPersonalInfo: ApplicantPersonalInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ApplicantPersonalInfos',
    },
    canActivate: [UserRouteAccessService],
  },
];
