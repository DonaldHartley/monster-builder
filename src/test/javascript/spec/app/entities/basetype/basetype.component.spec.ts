import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MonsterbuilderTestModule } from '../../../test.module';
import { BasetypeComponent } from 'app/entities/basetype/basetype.component';
import { BasetypeService } from 'app/entities/basetype/basetype.service';
import { Basetype } from 'app/shared/model/basetype.model';

describe('Component Tests', () => {
  describe('Basetype Management Component', () => {
    let comp: BasetypeComponent;
    let fixture: ComponentFixture<BasetypeComponent>;
    let service: BasetypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MonsterbuilderTestModule],
        declarations: [BasetypeComponent]
      })
        .overrideTemplate(BasetypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BasetypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BasetypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Basetype(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.basetypes && comp.basetypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
