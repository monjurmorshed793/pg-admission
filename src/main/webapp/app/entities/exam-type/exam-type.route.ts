import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IExamType, ExamType } from 'app/shared/model/exam-type.model';
import { ExamTypeService } from './exam-type.service';
import { ExamTypeComponent } from './exam-type.component';
import { ExamTypeDetailComponent } from './exam-type-detail.component';
import { ExamTypeUpdateComponent } from './exam-type-update.component';

@Injectable({ providedIn: 'root' })
export class ExamTypeResolve implements Resolve<IExamType> {
  constructor(private service: ExamTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExamType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((examType: HttpResponse<ExamType>) => {
          if (examType.body) {
            return of(examType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ExamType());
  }
}

export const examTypeRoute: Routes = [
  {
    path: '',
    component: ExamTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ExamTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExamTypeDetailComponent,
    resolve: {
      examType: ExamTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ExamTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExamTypeUpdateComponent,
    resolve: {
      examType: ExamTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ExamTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExamTypeUpdateComponent,
    resolve: {
      examType: ExamTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ExamTypes',
    },
    canActivate: [UserRouteAccessService],
  },
];
