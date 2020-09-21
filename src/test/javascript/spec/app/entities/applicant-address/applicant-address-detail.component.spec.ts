import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgadmissionTestModule } from '../../../test.module';
import { ApplicantAddressDetailComponent } from 'app/entities/applicant-address/applicant-address-detail.component';
import { ApplicantAddress } from 'app/shared/model/applicant-address.model';

describe('Component Tests', () => {
  describe('ApplicantAddress Management Detail Component', () => {
    let comp: ApplicantAddressDetailComponent;
    let fixture: ComponentFixture<ApplicantAddressDetailComponent>;
    const route = ({ data: of({ applicantAddress: new ApplicantAddress(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgadmissionTestModule],
        declarations: [ApplicantAddressDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ApplicantAddressDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApplicantAddressDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load applicantAddress on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.applicantAddress).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
