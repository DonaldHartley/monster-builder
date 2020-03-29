import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMonster } from 'app/shared/model/monster.model';
import { MonsterService } from './monster.service';

@Component({
  templateUrl: './monster-delete-dialog.component.html'
})
export class MonsterDeleteDialogComponent {
  monster?: IMonster;

  constructor(protected monsterService: MonsterService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.monsterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('monsterListModification');
      this.activeModal.close();
    });
  }
}
