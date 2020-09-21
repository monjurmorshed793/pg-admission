import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PgadmissionTestModule } from '../../../test.module';
import { ApplicantAddressUpdateComponent } from 'app/entities/applicant-address/applicant-address-update.component';
import { ApplicantAddressService } from 'app/entities/applicant-address/applicant-address.service';
import { ApplicantAddress } from 'app/shared/model/applicant-address.model';

describe('Component Tests', () => {
  describe('ApplicantAddress Management Update Component', () => {
    let comp: ApplicantAddressUpdateComponent;
    let fixture: ComponentFixture<ApplicantAddressUpdateComponent>;
    let service: ApplicantAddressService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgadmissionTestModule],
        declarations: [ApplicantAddressUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ApplicantAddressUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApplicantAddressUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicantAddressService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApplicantAddress(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApplicantAddress();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
