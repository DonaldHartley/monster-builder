import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMonster, Monster } from 'app/shared/model/monster.model';
import { MonsterService } from './monster.service';
import { MonsterComponent } from './monster.component';
import { MonsterDetailComponent } from './monster-detail.component';
import { MonsterUpdateComponent } from './monster-update.component';

@Injectable({ providedIn: 'root' })
export class MonsterResolve implements Resolve<IMonster> {
  constructor(private service: MonsterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMonster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((monster: HttpResponse<Monster>) => {
          if (monster.body) {
            return of(monster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Monster());
  }
}

export const monsterRoute: Routes = [
  {
    path: '',
    component: MonsterComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Monsters'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MonsterDetailComponent,
    resolve: {
      monster: MonsterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Monsters'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MonsterUpdateComponent,
    resolve: {
      monster: MonsterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Monsters'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MonsterUpdateComponent,
    resolve: {
      monster: MonsterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Monsters'
    },
    canActivate: [UserRouteAccessService]
  }
];
