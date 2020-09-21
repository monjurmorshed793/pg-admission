import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PgadmissionTestModule } from '../../../test.module';
import { ApplicantEducationalInformationUpdateComponent } from 'app/entities/applicant-educational-information/applicant-educational-information-update.component';
import { ApplicantEducationalInformationService } from 'app/entities/applicant-educational-information/applicant-educational-information.service';
import { ApplicantEducationalInformation } from 'app/shared/model/applicant-educational-information.model';

describe('Component Tests', () => {
  describe('ApplicantEducationalInformation Management Update Component', () => {
    let comp: ApplicantEducationalInformationUpdateComponent;
    let fixture: ComponentFixture<ApplicantEducationalInformationUpdateComponent>;
    let service: ApplicantEducationalInformationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgadmissionTestModule],
        declarations: [ApplicantEducationalInformationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ApplicantEducationalInformationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApplicantEducationalInformationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicantEducationalInformationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApplicantEducationalInformation(123);
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
        const entity = new ApplicantEducationalInformation();
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
