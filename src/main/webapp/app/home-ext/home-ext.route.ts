import { Route } from '@angular/router';

import { HomeExtComponent } from './home-ext.component';

export const HOME_EXT_ROUTE: Route = {
  path: '',
  component: HomeExtComponent,
  data: {
    authorities: [],
    pageTitle: 'Welcome to AUST!',
  },
};
