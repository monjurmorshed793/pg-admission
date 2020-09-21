import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'semester',
        loadChildren: () => import('./semester/semester.module').then(m => m.PgadmissionSemesterModule),
      },
      {
        path: 'program',
        loadChildren: () => import('./program/program.module').then(m => m.PgadmissionProgramModule),
      },
      {
        path: 'division',
        loadChildren: () => import('./division/division.module').then(m => m.PgadmissionDivisionModule),
      },
      {
        path: 'district',
        loadChildren: () => import('./district/district.module').then(m => m.PgadmissionDistrictModule),
      },
      {
        path: 'thana',
        loadChildren: () => import('./thana/thana.module').then(m => m.PgadmissionThanaModule),
      },
      {
        path: 'application-deadline',
        loadChildren: () => import('./application-deadline/application-deadline.module').then(m => m.PgadmissionApplicationDeadlineModule),
      },
      {
        path: 'applicant',
        loadChildren: () => import('./applicant/applicant.module').then(m => m.PgadmissionApplicantModule),
      },
      {
        path: 'applicant-personal-info',
        loadChildren: () =>
          import('./applicant-personal-info/applicant-personal-info.module').then(m => m.PgadmissionApplicantPersonalInfoModule),
      },
      {
        path: 'applicant-address',
        loadChildren: () => import('./applicant-address/applicant-address.module').then(m => m.PgadmissionApplicantAddressModule),
      },
      {
        path: 'exam-type',
        loadChildren: () => import('./exam-type/exam-type.module').then(m => m.PgadmissionExamTypeModule),
      },
      {
        path: 'applicant-educational-information',
        loadChildren: () =>
          import('./applicant-educational-information/applicant-educational-information.module').then(
            m => m.PgadmissionApplicantEducationalInformationModule
          ),
      },
      {
        path: 'job-experience',
        loadChildren: () => import('./job-experience/job-experience.module').then(m => m.PgadmissionJobExperienceModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class PgadmissionEntityModule {}
