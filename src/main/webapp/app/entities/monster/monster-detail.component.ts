import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMonster } from 'app/shared/model/monster.model';

@Component({
  selector: 'jhi-monster-detail',
  templateUrl: './monster-detail.component.html'
})
export class MonsterDetailComponent implements OnInit {
  monster: IMonster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ monster }) => (this.monster = monster));
  }

  previousState(): void {
    window.history.back();
  }
}
