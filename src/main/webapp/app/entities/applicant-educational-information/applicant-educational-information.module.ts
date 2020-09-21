import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PgadmissionSharedModule } from 'app/shared/shared.module';
import { ApplicantEducationalInformationComponent } from './applicant-educational-information.component';
import { ApplicantEducationalInformationDetailComponent } from './applicant-educational-information-detail.component';
import { ApplicantEducationalInformationUpdateComponent } from './applicant-educational-information-update.component';
import { ApplicantEducationalInformationDeleteDialogComponent } from './applicant-educational-information-delete-dialog.component';
import { applicantEducationalInformationRoute } from './applicant-educational-information.route';

@NgModule({
  imports: [PgadmissionSharedModule, RouterModule.forChild(applicantEducationalInformationRoute)],
  declarations: [
    ApplicantEducationalInformationComponent,
    ApplicantEducationalInformationDetailComponent,
    ApplicantEducationalInformationUpdateComponent,
    ApplicantEducationalInformationDeleteDialogComponent,
  ],
  entryComponents: [ApplicantEducationalInformationDeleteDialogComponent],
})
export class PgadmissionApplicantEducationalInformationModule {}
