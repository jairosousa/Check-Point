import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IControle } from 'app/shared/model/controle.model';

@Component({
    selector: 'jhi-controle-detail',
    templateUrl: './controle-detail.component.html'
})
export class ControleDetailComponent implements OnInit {
    controle: IControle;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ controle }) => {
            this.controle = controle;
        });
    }

    previousState() {
        window.history.back();
    }
}
