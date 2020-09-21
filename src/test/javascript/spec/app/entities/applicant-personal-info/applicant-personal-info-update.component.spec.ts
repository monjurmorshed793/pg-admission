import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PgadmissionTestModule } from '../../../test.module';
import { ApplicantPersonalInfoUpdateComponent } from 'app/entities/applicant-personal-info/applicant-personal-info-update.component';
import { ApplicantPersonalInfoService } from 'app/entities/applicant-personal-info/applicant-personal-info.service';
import { ApplicantPersonalInfo } from 'app/shared/model/applicant-personal-info.model';

describe('Component Tests', () => {
  describe('ApplicantPersonalInfo Management Update Component', () => {
    let comp: ApplicantPersonalInfoUpdateComponent;
    let fixture: ComponentFixture<ApplicantPersonalInfoUpdateComponent>;
    let service: ApplicantPersonalInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgadmissionTestModule],
        declarations: [ApplicantPersonalInfoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ApplicantPersonalInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApplicantPersonalInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicantPersonalInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApplicantPersonalInfo(123);
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
        const entity = new ApplicantPersonalInfo();
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
