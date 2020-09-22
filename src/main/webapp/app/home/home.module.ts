import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PgadmissionSharedModule } from 'app/shared/shared.module';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [PgadmissionSharedModule],
  declarations: [HomeComponent],
})
export class PgadmissionHomeModule {}
