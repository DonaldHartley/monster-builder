import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MonsterbuilderTestModule } from '../../../test.module';
import { MonsterComponent } from 'app/entities/monster/monster.component';
import { MonsterService } from 'app/entities/monster/monster.service';
import { Monster } from 'app/shared/model/monster.model';

describe('Component Tests', () => {
  describe('Monster Management Component', () => {
    let comp: MonsterComponent;
    let fixture: ComponentFixture<MonsterComponent>;
    let service: MonsterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MonsterbuilderTestModule],
        declarations: [MonsterComponent]
      })
        .overrideTemplate(MonsterComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MonsterComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MonsterService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Monster(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.monsters && comp.monsters[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
