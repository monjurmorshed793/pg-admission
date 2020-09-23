import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PgadmissionSharedModule } from 'app/shared/shared.module';
import { HomeExtComponent } from './home-ext.component';

@NgModule({
  imports: [PgadmissionSharedModule],
  declarations: [HomeExtComponent],
})
export class PgadmissionExtHomeModule {}
