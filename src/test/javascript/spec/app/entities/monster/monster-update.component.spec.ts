import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MonsterbuilderTestModule } from '../../../test.module';
import { MonsterUpdateComponent } from 'app/entities/monster/monster-update.component';
import { MonsterService } from 'app/entities/monster/monster.service';
import { Monster } from 'app/shared/model/monster.model';

describe('Component Tests', () => {
  describe('Monster Management Update Component', () => {
    let comp: MonsterUpdateComponent;
    let fixture: ComponentFixture<MonsterUpdateComponent>;
    let service: MonsterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MonsterbuilderTestModule],
        declarations: [MonsterUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MonsterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MonsterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MonsterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Monster(123);
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
        const entity = new Monster();
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
