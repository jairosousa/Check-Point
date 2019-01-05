import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import 'moment/locale/pt-br';

import { IControle } from 'app/shared/model/controle.model';
import { ControleService } from './controle.service';

@Component({
    selector: 'jhi-controle-update',
    templateUrl: './controle-update.component.html'
})
export class ControleUpdateComponent implements OnInit {
    currentAction: string;
    controle: IControle;
    isSaving: boolean;
    dataDp: any;

    horas = ['0:00', '1:00', '', ''];

    constructor(
        protected controleService: ControleService,
        protected activatedRoute: ActivatedRoute,
        private route: ActivatedRoute,
        private router: Router
    ) {}

    ngOnInit() {
        this.setCurrentyAction();
        moment.locale('pt-BR');
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ controle }) => {
            this.controle = controle;
        });
        if (!this.controle.id) {
            this.controle.data = moment();
            this.controle.corPulseira = '#FFF';
        }
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        console.log(this.controle);
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

    // PRIVATE METHODOS
    private setCurrentyAction() {
        if (this.route.snapshot.url[1].path == 'new') {
            this.currentAction = 'new';
        } else {
            this.currentAction = 'edit';
        }
    }
}
