import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IControle } from 'app/shared/model/controle.model';
import { ControleService } from './controle.service';

@Component({
    selector: 'jhi-controle-update',
    templateUrl: './controle-update.component.html'
})
export class ControleUpdateComponent implements OnInit {
    controle: IControle;
    isSaving: boolean;
    dataDp: any;

    constructor(protected controleService: ControleService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ controle }) => {
            this.controle = controle;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.controle.id !== undefined) {
            this.subscribeToSaveResponse(this.controleService.update(this.controle));
        } else {
            this.subscribeToSaveResponse(this.controleService.create(this.controle));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IControle>>) {
        result.subscribe((res: HttpResponse<IControle>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
