import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMonster } from 'app/shared/model/monster.model';
import { MonsterService } from './monster.service';
import { MonsterDeleteDialogComponent } from './monster-delete-dialog.component';

@Component({
  selector: 'jhi-monster',
  templateUrl: './monster.component.html'
})
export class MonsterComponent implements OnInit, OnDestroy {
  monsters?: IMonster[];
  eventSubscriber?: Subscription;

  constructor(protected monsterService: MonsterService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.monsterService.query().subscribe((res: HttpResponse<IMonster[]>) => (this.monsters = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMonsters();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMonster): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMonsters(): void {
    this.eventSubscriber = this.eventManager.subscribe('monsterListModification', () => this.loadAll());
  }

  delete(monster: IMonster): void {
    const modalRef = this.modalService.open(MonsterDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.monster = monster;
  }
}
