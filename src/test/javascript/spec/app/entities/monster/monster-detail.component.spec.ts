import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MonsterbuilderTestModule } from '../../../test.module';
import { MonsterDetailComponent } from 'app/entities/monster/monster-detail.component';
import { Monster } from 'app/shared/model/monster.model';

describe('Component Tests', () => {
  describe('Monster Management Detail Component', () => {
    let comp: MonsterDetailComponent;
    let fixture: ComponentFixture<MonsterDetailComponent>;
    const route = ({ data: of({ monster: new Monster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MonsterbuilderTestModule],
        declarations: [MonsterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MonsterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MonsterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load monster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.monster).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
