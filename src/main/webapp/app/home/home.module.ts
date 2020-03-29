import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MonsterbuilderSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { MonsterComponent } from 'app/entities/monster/monster.component';

@NgModule({
  imports: [MonsterbuilderSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent, MonsterComponent]
})
export class MonsterbuilderHomeModule {}
