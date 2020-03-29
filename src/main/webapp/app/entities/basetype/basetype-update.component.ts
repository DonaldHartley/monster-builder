import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBasetype, Basetype } from 'app/shared/model/basetype.model';
import { BasetypeService } from './basetype.service';

@Component({
  selector: 'jhi-basetype-update',
  templateUrl: './basetype-update.component.html'
})
export class BasetypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]]
  });

  constructor(protected basetypeService: BasetypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ basetype }) => {
      this.updateForm(basetype);
    });
  }

  updateForm(basetype: IBasetype): void {
    this.editForm.patchValue({
      id: basetype.id,
      name: basetype.name
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const basetype = this.createFromForm();
    if (basetype.id !== undefined) {
      this.subscribeToSaveResponse(this.basetypeService.update(basetype));
    } else {
      this.subscribeToSaveResponse(this.basetypeService.create(basetype));
    }
  }

  private createFromForm(): IBasetype {
    return {
      ...new Basetype(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBasetype>>): void {
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
}
