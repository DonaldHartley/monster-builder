import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MonsterbuilderSharedModule } from 'app/shared/shared.module';
import { BasetypeComponent } from './basetype.component';
import { BasetypeDetailComponent } from './basetype-detail.component';
import { BasetypeUpdateComponent } from './basetype-update.component';
import { BasetypeDeleteDialogComponent } from './basetype-delete-dialog.component';
import { basetypeRoute } from './basetype.route';

@NgModule({
  imports: [MonsterbuilderSharedModule, RouterModule.forChild(basetypeRoute)],
  declarations: [BasetypeComponent, BasetypeDetailComponent, BasetypeUpdateComponent, BasetypeDeleteDialogComponent],
  entryComponents: [BasetypeDeleteDialogComponent]
})
export class MonsterbuilderBasetypeModule {}
