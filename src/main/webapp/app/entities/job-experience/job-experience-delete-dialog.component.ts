import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJobExperience } from 'app/shared/model/job-experience.model';
import { JobExperienceService } from './job-experience.service';

@Component({
  templateUrl: './job-experience-delete-dialog.component.html',
})
export class JobExperienceDeleteDialogComponent {
  jobExperience?: IJobExperience;

  constructor(
    protected jobExperienceService: JobExperienceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jobExperienceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('jobExperienceListModification');
      this.activeModal.close();
    });
  }
}
