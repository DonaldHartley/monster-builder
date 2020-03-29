import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBasetype, Basetype } from 'app/shared/model/basetype.model';
import { BasetypeService } from './basetype.service';
import { BasetypeComponent } from './basetype.component';
import { BasetypeDetailComponent } from './basetype-detail.component';
import { BasetypeUpdateComponent } from './basetype-update.component';

@Injectable({ providedIn: 'root' })
export class BasetypeResolve implements Resolve<IBasetype> {
  constructor(private service: BasetypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBasetype> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((basetype: HttpResponse<Basetype>) => {
          if (basetype.body) {
            return of(basetype.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Basetype());
  }
}

export const basetypeRoute: Routes = [
  {
    path: '',
    component: BasetypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Basetypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BasetypeDetailComponent,
    resolve: {
      basetype: BasetypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Basetypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BasetypeUpdateComponent,
    resolve: {
      basetype: BasetypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Basetypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BasetypeUpdateComponent,
    resolve: {
      basetype: BasetypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Basetypes'
    },
    canActivate: [UserRouteAccessService]
  }
];
