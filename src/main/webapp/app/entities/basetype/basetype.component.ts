import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBasetype } from 'app/shared/model/basetype.model';
import { BasetypeService } from './basetype.service';
import { BasetypeDeleteDialogComponent } from './basetype-delete-dialog.component';

@Component({
  selector: 'jhi-basetype',
  templateUrl: './basetype.component.html'
})
export class BasetypeComponent implements OnInit, OnDestroy {
  basetypes?: IBasetype[];
  eventSubscriber?: Subscription;

  constructor(protected basetypeService: BasetypeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.basetypeService.query().subscribe((res: HttpResponse<IBasetype[]>) => (this.basetypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBasetypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBasetype): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBasetypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('basetypeListModification', () => this.loadAll());
  }

  delete(basetype: IBasetype): void {
    const modalRef = this.modalService.open(BasetypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.basetype = basetype;
  }
}
