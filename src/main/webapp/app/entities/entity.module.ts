import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'monster',
        loadChildren: () => import('./monster/monster.module').then(m => m.MonsterbuilderMonsterModule)
      },
      {
        path: 'basetype',
        loadChildren: () => import('./basetype/basetype.module').then(m => m.MonsterbuilderBasetypeModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class MonsterbuilderEntityModule {}
