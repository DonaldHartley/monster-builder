import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MonsterbuilderSharedModule } from 'app/shared/shared.module';
import { MonsterComponent } from './monster.component';
import { MonsterDetailComponent } from './monster-detail.component';
import { MonsterUpdateComponent } from './monster-update.component';
import { MonsterDeleteDialogComponent } from './monster-delete-dialog.component';
import { monsterRoute } from './monster.route';

@NgModule({
  imports: [MonsterbuilderSharedModule, RouterModule.forChild(monsterRoute)],
  declarations: [MonsterComponent, MonsterDetailComponent, MonsterUpdateComponent, MonsterDeleteDialogComponent],
  entryComponents: [MonsterDeleteDialogComponent]
})
export class MonsterbuilderMonsterModule {}
