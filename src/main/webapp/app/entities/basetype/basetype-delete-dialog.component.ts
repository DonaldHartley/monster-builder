import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBasetype } from 'app/shared/model/basetype.model';
import { BasetypeService } from './basetype.service';

@Component({
  templateUrl: './basetype-delete-dialog.component.html'
})
export class BasetypeDeleteDialogComponent {
  basetype?: IBasetype;

  constructor(protected basetypeService: BasetypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.basetypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('basetypeListModification');
      this.activeModal.close();
    });
  }
}
