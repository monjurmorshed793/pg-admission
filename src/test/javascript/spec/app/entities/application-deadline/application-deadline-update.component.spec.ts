import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PgadmissionTestModule } from '../../../test.module';
import { ApplicationDeadlineUpdateComponent } from 'app/entities/application-deadline/application-deadline-update.component';
import { ApplicationDeadlineService } from 'app/entities/application-deadline/application-deadline.service';
import { ApplicationDeadline } from 'app/shared/model/application-deadline.model';

describe('Component Tests', () => {
  describe('ApplicationDeadline Management Update Component', () => {
    let comp: ApplicationDeadlineUpdateComponent;
    let fixture: ComponentFixture<ApplicationDeadlineUpdateComponent>;
    let service: ApplicationDeadlineService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgadmissionTestModule],
        declarations: [ApplicationDeadlineUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ApplicationDeadlineUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApplicationDeadlineUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicationDeadlineService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApplicationDeadline(123);
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
        const entity = new ApplicationDeadline();
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
