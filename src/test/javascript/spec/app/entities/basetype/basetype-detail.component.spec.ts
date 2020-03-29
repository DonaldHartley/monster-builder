import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MonsterbuilderTestModule } from '../../../test.module';
import { BasetypeDetailComponent } from 'app/entities/basetype/basetype-detail.component';
import { Basetype } from 'app/shared/model/basetype.model';

describe('Component Tests', () => {
  describe('Basetype Management Detail Component', () => {
    let comp: BasetypeDetailComponent;
    let fixture: ComponentFixture<BasetypeDetailComponent>;
    const route = ({ data: of({ basetype: new Basetype(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MonsterbuilderTestModule],
        declarations: [BasetypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BasetypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BasetypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load basetype on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.basetype).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
