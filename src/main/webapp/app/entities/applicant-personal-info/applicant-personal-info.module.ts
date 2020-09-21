import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PgadmissionSharedModule } from 'app/shared/shared.module';
import { ApplicantPersonalInfoComponent } from './applicant-personal-info.component';
import { ApplicantPersonalInfoDetailComponent } from './applicant-personal-info-detail.component';
import { ApplicantPersonalInfoUpdateComponent } from './applicant-personal-info-update.component';
import { ApplicantPersonalInfoDeleteDialogComponent } from './applicant-personal-info-delete-dialog.component';
import { applicantPersonalInfoRoute } from './applicant-personal-info.route';

@NgModule({
  imports: [PgadmissionSharedModule, RouterModule.forChild(applicantPersonalInfoRoute)],
  declarations: [
    ApplicantPersonalInfoComponent,
    ApplicantPersonalInfoDetailComponent,
    ApplicantPersonalInfoUpdateComponent,
    ApplicantPersonalInfoDeleteDialogComponent,
  ],
  entryComponents: [ApplicantPersonalInfoDeleteDialogComponent],
})
export class PgadmissionApplicantPersonalInfoModule {}
