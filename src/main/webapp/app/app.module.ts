import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { PgadmissionSharedModule } from 'app/shared/shared.module';
import { PgadmissionCoreModule } from 'app/core/core.module';
import { PgadmissionAppRoutingModule } from './app-routing.module';
import { PgadmissionHomeModule } from './home/home.module';
import { PgadmissionEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';
import { NavbarExtComponent } from './layouts/navbar-ext/navbar-ext.component';
import { PgadmissionExtHomeModule } from 'app/home-ext/home-ext.module';

@NgModule({
  imports: [
    BrowserModule,
    PgadmissionSharedModule,
    PgadmissionCoreModule,
    PgadmissionExtHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    PgadmissionEntityModule,
    PgadmissionAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent, NavbarExtComponent],
  bootstrap: [MainComponent],
})
export class PgadmissionAppModule {}
