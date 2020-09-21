import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import {
  IApplicantEducationalInformation,
  ApplicantEducationalInformation,
} from 'app/shared/model/applicant-educational-information.model';
import { ApplicantEducationalInformationService } from './applicant-educational-information.service';
import { ApplicantEducationalInformationComponent } from './applicant-educational-information.component';
import { ApplicantEducationalInformationDetailComponent } from './applicant-educational-information-detail.component';
import { ApplicantEducationalInformationUpdateComponent } from './applicant-educational-information-update.component';

@Injectable({ providedIn: 'root' })
export class ApplicantEducationalInformationResolve implements Resolve<IApplicantEducationalInformation> {
  constructor(private service: ApplicantEducationalInformationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IApplicantEducationalInformation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((applicantEducationalInformation: HttpResponse<ApplicantEducationalInformation>) => {
          if (applicantEducationalInformation.body) {
            return of(applicantEducationalInformation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ApplicantEducationalInformation());
  }
}

export const applicantEducationalInformationRoute: Routes = [
  {
    path: '',
    component: ApplicantEducationalInformationComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ApplicantEducationalInformations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ApplicantEducationalInformationDetailComponent,
    resolve: {
      applicantEducationalInformation: ApplicantEducationalInformationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ApplicantEducationalInformations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ApplicantEducationalInformationUpdateComponent,
    resolve: {
      applicantEducationalInformation: ApplicantEducationalInformationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ApplicantEducationalInformations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ApplicantEducationalInformationUpdateComponent,
    resolve: {
      applicantEducationalInformation: ApplicantEducationalInformationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ApplicantEducationalInformations',
    },
    canActivate: [UserRouteAccessService],
  },
];
