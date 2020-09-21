import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PgadmissionTestModule } from '../../../test.module';
import { ProgramUpdateComponent } from 'app/entities/program/program-update.component';
import { ProgramService } from 'app/entities/program/program.service';
import { Program } from 'app/shared/model/program.model';

describe('Component Tests', () => {
  describe('Program Management Update Component', () => {
    let comp: ProgramUpdateComponent;
    let fixture: ComponentFixture<ProgramUpdateComponent>;
    let service: ProgramService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgadmissionTestModule],
        declarations: [ProgramUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProgramUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProgramUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProgramService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Program(123);
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
        const entity = new Program();
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
