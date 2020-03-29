import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MonsterbuilderTestModule } from '../../../test.module';
import { BasetypeUpdateComponent } from 'app/entities/basetype/basetype-update.component';
import { BasetypeService } from 'app/entities/basetype/basetype.service';
import { Basetype } from 'app/shared/model/basetype.model';

describe('Component Tests', () => {
  describe('Basetype Management Update Component', () => {
    let comp: BasetypeUpdateComponent;
    let fixture: ComponentFixture<BasetypeUpdateComponent>;
    let service: BasetypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MonsterbuilderTestModule],
        declarations: [BasetypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BasetypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BasetypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BasetypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Basetype(123);
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
        const entity = new Basetype();
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
