import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PgadmissionTestModule } from '../../../test.module';
import { JobExperienceUpdateComponent } from 'app/entities/job-experience/job-experience-update.component';
import { JobExperienceService } from 'app/entities/job-experience/job-experience.service';
import { JobExperience } from 'app/shared/model/job-experience.model';

describe('Component Tests', () => {
  describe('JobExperience Management Update Component', () => {
    let comp: JobExperienceUpdateComponent;
    let fixture: ComponentFixture<JobExperienceUpdateComponent>;
    let service: JobExperienceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgadmissionTestModule],
        declarations: [JobExperienceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(JobExperienceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JobExperienceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JobExperienceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new JobExperience(123);
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
        const entity = new JobExperience();
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
