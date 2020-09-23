import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PgadmissionSharedModule } from 'app/shared/shared.module';
import { HomeExtComponent } from './home-ext.component';
import { HomeComponent } from 'app/home/home.component';
import { HOME_ROUTE } from 'app/home/home.route';
import { HOME_EXT_ROUTE } from 'app/home-ext/home-ext.route';

@NgModule({
  imports: [PgadmissionSharedModule, RouterModule.forChild([HOME_EXT_ROUTE])],
  declarations: [HomeExtComponent, HomeComponent],
})
export class PgadmissionExtHomeModule {}
