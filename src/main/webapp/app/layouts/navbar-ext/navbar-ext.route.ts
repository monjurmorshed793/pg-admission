import { Route } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar.component';
import { NavbarExtComponent } from 'app/layouts/navbar-ext/navbar-ext.component';

export const navbarExtRoute: Route = {
  path: '',
  component: NavbarExtComponent,
  outlet: 'navbar',
};
