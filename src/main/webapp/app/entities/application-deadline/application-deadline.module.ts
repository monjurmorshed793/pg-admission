import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PgadmissionSharedModule } from 'app/shared/shared.module';
import { ApplicationDeadlineComponent } from './application-deadline.component';
import { ApplicationDeadlineDetailComponent } from './application-deadline-detail.component';
import { ApplicationDeadlineUpdateComponent } from './application-deadline-update.component';
import { ApplicationDeadlineDeleteDialogComponent } from './application-deadline-delete-dialog.component';
import { applicationDeadlineRoute } from './application-deadline.route';

@NgModule({
  imports: [PgadmissionSharedModule, RouterModule.forChild(applicationDeadlineRoute)],
  declarations: [
    ApplicationDeadlineComponent,
    ApplicationDeadlineDetailComponent,
    ApplicationDeadlineUpdateComponent,
    ApplicationDeadlineDeleteDialogComponent,
  ],
  entryComponents: [ApplicationDeadlineDeleteDialogComponent],
})
export class PgadmissionApplicationDeadlineModule {}
