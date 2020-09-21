import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IApplicationDeadline, ApplicationDeadline } from 'app/shared/model/application-deadline.model';
import { ApplicationDeadlineService } from './application-deadline.service';
import { ApplicationDeadlineComponent } from './application-deadline.component';
import { ApplicationDeadlineDetailComponent } from './application-deadline-detail.component';
import { ApplicationDeadlineUpdateComponent } from './application-deadline-update.component';

@Injectable({ providedIn: 'root' })
export class ApplicationDeadlineResolve implements Resolve<IApplicationDeadline> {
  constructor(private service: ApplicationDeadlineService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IApplicationDeadline> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((applicationDeadline: HttpResponse<ApplicationDeadline>) => {
          if (applicationDeadline.body) {
            return of(applicationDeadline.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ApplicationDeadline());
  }
}

export const applicationDeadlineRoute: Routes = [
  {
    path: '',
    component: ApplicationDeadlineComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ApplicationDeadlines',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ApplicationDeadlineDetailComponent,
    resolve: {
      applicationDeadline: ApplicationDeadlineResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ApplicationDeadlines',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ApplicationDeadlineUpdateComponent,
    resolve: {
      applicationDeadline: ApplicationDeadlineResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ApplicationDeadlines',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ApplicationDeadlineUpdateComponent,
    resolve: {
      applicationDeadline: ApplicationDeadlineResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ApplicationDeadlines',
    },
    canActivate: [UserRouteAccessService],
  },
];
