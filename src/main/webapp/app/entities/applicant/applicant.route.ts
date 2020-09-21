import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IApplicant, Applicant } from 'app/shared/model/applicant.model';
import { ApplicantService } from './applicant.service';
import { ApplicantComponent } from './applicant.component';
import { ApplicantDetailComponent } from './applicant-detail.component';
import { ApplicantUpdateComponent } from './applicant-update.component';

@Injectable({ providedIn: 'root' })
export class ApplicantResolve implements Resolve<IApplicant> {
  constructor(private service: ApplicantService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IApplicant> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((applicant: HttpResponse<Applicant>) => {
          if (applicant.body) {
            return of(applicant.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Applicant());
  }
}

export const applicantRoute: Routes = [
  {
    path: '',
    component: ApplicantComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Applicants',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ApplicantDetailComponent,
    resolve: {
      applicant: ApplicantResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Applicants',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ApplicantUpdateComponent,
    resolve: {
      applicant: ApplicantResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Applicants',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ApplicantUpdateComponent,
    resolve: {
      applicant: ApplicantResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Applicants',
    },
    canActivate: [UserRouteAccessService],
  },
];
