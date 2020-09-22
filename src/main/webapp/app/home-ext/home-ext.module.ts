import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HomeExtComponent } from './home-ext.component';
import { PgadmissionSharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';
import { HOME_ROUTE } from '../home/home.route';
import { HomeComponent } from '../home/home.component';
import { HOME_EXT_ROUTE } from './home-ext.route';

@NgModule({
  imports: [PgadmissionSharedModule, RouterModule.forChild([HOME_EXT_ROUTE])],
  declarations: [HomeExtComponent],
})
export class PgAdmissionHomeExtModule {}
