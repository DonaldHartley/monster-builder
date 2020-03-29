import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IMonster, Monster } from 'app/shared/model/monster.model';
import { MonsterService } from './monster.service';
import { IBasetype } from 'app/shared/model/basetype.model';
import { BasetypeService } from 'app/entities/basetype/basetype.service';

@Component({
  selector: 'jhi-monster-update',
  templateUrl: './monster-update.component.html'
})
export class MonsterUpdateComponent implements OnInit {
  isSaving = false;
  basetypes: IBasetype[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    creatorId: [],
    basetype: []
  });

  constructor(
    protected monsterService: MonsterService,
    protected basetypeService: BasetypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ monster }) => {
      this.updateForm(monster);

      this.basetypeService
        .query({ filter: 'monster-is-null' })
        .pipe(
          map((res: HttpResponse<IBasetype[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IBasetype[]) => {
          if (!monster.basetype || !monster.basetype.id) {
            this.basetypes = resBody;
          } else {
            this.basetypeService
              .find(monster.basetype.id)
              .pipe(
                map((subRes: HttpResponse<IBasetype>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IBasetype[]) => (this.basetypes = concatRes));
          }
        });
    });
  }

  updateForm(monster: IMonster): void {
    this.editForm.patchValue({
      id: monster.id,
      name: monster.name,
      creatorId: monster.creatorId,
      basetype: monster.basetype
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const monster = this.createFromForm();
    if (monster.id !== undefined) {
      this.subscribeToSaveResponse(this.monsterService.update(monster));
    } else {
      this.subscribeToSaveResponse(this.monsterService.create(monster));
    }
  }

  private createFromForm(): IMonster {
    return {
      ...new Monster(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      creatorId: this.editForm.get(['creatorId'])!.value,
      basetype: this.editForm.get(['basetype'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMonster>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IBasetype): any {
    return item.id;
  }
}
