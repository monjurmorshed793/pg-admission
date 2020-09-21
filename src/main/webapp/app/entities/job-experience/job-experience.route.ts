import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IJobExperience, JobExperience } from 'app/shared/model/job-experience.model';
import { JobExperienceService } from './job-experience.service';
import { JobExperienceComponent } from './job-experience.component';
import { JobExperienceDetailComponent } from './job-experience-detail.component';
import { JobExperienceUpdateComponent } from './job-experience-update.component';

@Injectable({ providedIn: 'root' })
export class JobExperienceResolve implements Resolve<IJobExperience> {
  constructor(private service: JobExperienceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJobExperience> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((jobExperience: HttpResponse<JobExperience>) => {
          if (jobExperience.body) {
            return of(jobExperience.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new JobExperience());
  }
}

export const jobExperienceRoute: Routes = [
  {
    path: '',
    component: JobExperienceComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'JobExperiences',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JobExperienceDetailComponent,
    resolve: {
      jobExperience: JobExperienceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'JobExperiences',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JobExperienceUpdateComponent,
    resolve: {
      jobExperience: JobExperienceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'JobExperiences',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JobExperienceUpdateComponent,
    resolve: {
      jobExperience: JobExperienceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'JobExperiences',
    },
    canActivate: [UserRouteAccessService],
  },
];
