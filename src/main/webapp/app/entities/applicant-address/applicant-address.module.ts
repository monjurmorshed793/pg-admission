import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PgadmissionSharedModule } from 'app/shared/shared.module';
import { ApplicantAddressComponent } from './applicant-address.component';
import { ApplicantAddressDetailComponent } from './applicant-address-detail.component';
import { ApplicantAddressUpdateComponent } from './applicant-address-update.component';
import { ApplicantAddressDeleteDialogComponent } from './applicant-address-delete-dialog.component';
import { applicantAddressRoute } from './applicant-address.route';

@NgModule({
  imports: [PgadmissionSharedModule, RouterModule.forChild(applicantAddressRoute)],
  declarations: [
    ApplicantAddressComponent,
    ApplicantAddressDetailComponent,
    ApplicantAddressUpdateComponent,
    ApplicantAddressDeleteDialogComponent,
  ],
  entryComponents: [ApplicantAddressDeleteDialogComponent],
})
export class PgadmissionApplicantAddressModule {}
