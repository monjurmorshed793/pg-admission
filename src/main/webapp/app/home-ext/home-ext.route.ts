import { NgModule } from '@angular/core';
import { Routes, RouterModule, Route } from '@angular/router';

import { HomeExtComponent } from './home-ext.component';
import { HomeComponent } from '../home/home.component';

export const HOME_EXT_ROUTE: Route = {
  path: '',
  component: HomeExtComponent,
  data: {
    authorities: [],
    pageTitle: 'Welcome to AUST PG Admission',
  },
};
