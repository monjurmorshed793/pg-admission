import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApplicationDeadline } from 'app/shared/model/application-deadline.model';

@Component({
  selector: 'jhi-application-deadline-detail',
  templateUrl: './application-deadline-detail.component.html',
})
export class ApplicationDeadlineDetailComponent implements OnInit {
  applicationDeadline: IApplicationDeadline | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ applicationDeadline }) => (this.applicationDeadline = applicationDeadline));
  }

  previousState(): void {
    window.history.back();
  }
}
