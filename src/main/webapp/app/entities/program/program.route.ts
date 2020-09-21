import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProgram, Program } from 'app/shared/model/program.model';
import { ProgramService } from './program.service';
import { ProgramComponent } from './program.component';
import { ProgramDetailComponent } from './program-detail.component';
import { ProgramUpdateComponent } from './program-update.component';

@Injectable({ providedIn: 'root' })
export class ProgramResolve implements Resolve<IProgram> {
  constructor(private service: ProgramService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProgram> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((program: HttpResponse<Program>) => {
          if (program.body) {
            return of(program.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Program());
  }
}

export const programRoute: Routes = [
  {
    path: '',
    component: ProgramComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Programs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProgramDetailComponent,
    resolve: {
      program: ProgramResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Programs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProgramUpdateComponent,
    resolve: {
      program: ProgramResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Programs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProgramUpdateComponent,
    resolve: {
      program: ProgramResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Programs',
    },
    canActivate: [UserRouteAccessService],
  },
];
