import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBasetype } from 'app/shared/model/basetype.model';

@Component({
  selector: 'jhi-basetype-detail',
  templateUrl: './basetype-detail.component.html'
})
export class BasetypeDetailComponent implements OnInit {
  basetype: IBasetype | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ basetype }) => (this.basetype = basetype));
  }

  previousState(): void {
    window.history.back();
  }
}
