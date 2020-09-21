import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PgadmissionSharedModule } from 'app/shared/shared.module';
import { JobExperienceComponent } from './job-experience.component';
import { JobExperienceDetailComponent } from './job-experience-detail.component';
import { JobExperienceUpdateComponent } from './job-experience-update.component';
import { JobExperienceDeleteDialogComponent } from './job-experience-delete-dialog.component';
import { jobExperienceRoute } from './job-experience.route';

@NgModule({
  imports: [PgadmissionSharedModule, RouterModule.forChild(jobExperienceRoute)],
  declarations: [JobExperienceComponent, JobExperienceDetailComponent, JobExperienceUpdateComponent, JobExperienceDeleteDialogComponent],
  entryComponents: [JobExperienceDeleteDialogComponent],
})
export class PgadmissionJobExperienceModule {}
