import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PgadmissionSharedModule } from 'app/shared/shared.module';
import { ThanaComponent } from './thana.component';
import { ThanaDetailComponent } from './thana-detail.component';
import { ThanaUpdateComponent } from './thana-update.component';
import { ThanaDeleteDialogComponent } from './thana-delete-dialog.component';
import { thanaRoute } from './thana.route';

@NgModule({
  imports: [PgadmissionSharedModule, RouterModule.forChild(thanaRoute)],
  declarations: [ThanaComponent, ThanaDetailComponent, ThanaUpdateComponent, ThanaDeleteDialogComponent],
  entryComponents: [ThanaDeleteDialogComponent],
})
export class PgadmissionThanaModule {}
