import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApplicantPersonalInfo } from 'app/shared/model/applicant-personal-info.model';
import { ApplicantPersonalInfoService } from './applicant-personal-info.service';

@Component({
  templateUrl: './applicant-personal-info-delete-dialog.component.html',
})
export class ApplicantPersonalInfoDeleteDialogComponent {
  applicantPersonalInfo?: IApplicantPersonalInfo;

  constructor(
    protected applicantPersonalInfoService: ApplicantPersonalInfoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.applicantPersonalInfoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('applicantPersonalInfoListModification');
      this.activeModal.close();
    });
  }
}
