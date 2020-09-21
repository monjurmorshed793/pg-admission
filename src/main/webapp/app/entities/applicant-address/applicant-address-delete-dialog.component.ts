import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApplicantAddress } from 'app/shared/model/applicant-address.model';
import { ApplicantAddressService } from './applicant-address.service';

@Component({
  templateUrl: './applicant-address-delete-dialog.component.html',
})
export class ApplicantAddressDeleteDialogComponent {
  applicantAddress?: IApplicantAddress;

  constructor(
    protected applicantAddressService: ApplicantAddressService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.applicantAddressService.delete(id).subscribe(() => {
      this.eventManager.broadcast('applicantAddressListModification');
      this.activeModal.close();
    });
  }
}
