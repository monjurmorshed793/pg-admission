import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PgadmissionSharedModule } from 'app/shared/shared.module';
import { ApplicantComponent } from './applicant.component';
import { ApplicantDetailComponent } from './applicant-detail.component';
import { ApplicantUpdateComponent } from './applicant-update.component';
import { ApplicantDeleteDialogComponent } from './applicant-delete-dialog.component';
import { applicantRoute } from './applicant.route';

@NgModule({
  imports: [PgadmissionSharedModule, RouterModule.forChild(applicantRoute)],
  declarations: [ApplicantComponent, ApplicantDetailComponent, ApplicantUpdateComponent, ApplicantDeleteDialogComponent],
  entryComponents: [ApplicantDeleteDialogComponent],
})
export class PgadmissionApplicantModule {}
