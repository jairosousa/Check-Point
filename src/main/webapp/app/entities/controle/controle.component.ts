import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { IControle } from 'app/shared/model/controle.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { ControleService } from './controle.service';

import * as jsPDF from 'jspdf';
import { faFilePdf } from '@fortawesome/free-solid-svg-icons';

@Component({
    selector: 'jhi-controle',
    templateUrl: './controle.component.html'
})
export class ControleComponent implements OnInit, OnDestroy {
    currentAccount: any;
    controles: IControle[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    pdf = faFilePdf;

    constructor(
        protected controleService: ControleService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.controleService
                .search({
                    page: this.page - 1,
                    query: this.currentSearch,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IControle[]>) => this.paginateControles(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.controleService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IControle[]>) => this.paginateControles(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/controle'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                search: this.currentSearch,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.currentSearch = '';
        this.router.navigate([
            '/controle',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.page = 0;
        this.currentSearch = query;
        this.router.navigate([
            '/controle',
            {
                search: this.currentSearch,
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInControles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IControle) {
        return item.id;
    }

    registerChangeInControles() {
        this.eventSubscriber = this.eventManager.subscribe('controleListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateControles(data: IControle[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.controles = data;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    gerarPDF() {
        const doc = new jsPDF();
        doc.setProperties({
            title: 'controle-ponto'
        });
        doc.setFont('Courier');
        doc.setFontStyle('bold');
        doc.setFontSize(20);
        doc.text('Controle de Ponto', 105, 20, null, null, 'center');
        doc.line(78, 24, 133, 24);

        doc.setFontSize(12);
        doc.text('Data', 15, 40);
        doc.text('Entrada', 43, 40);
        doc.text('Almoço', 65, 40);
        doc.text('Retorno', 83, 40);
        doc.text('Saída', 103, 40);
        doc.text('Observação', 120, 40);

        doc.line(15, 42, 140, 42);

        let y = 47;
        this.controles.forEach(controle => {
            const observacao = !controle.observacao ? '' : controle.observacao;
            doc.text(this.formatData(controle.data), 15, y);
            doc.text(`${controle.hrEntrada}`, 44, y);
            doc.text(`${controle.hrAlmocoSaida}`, 66, y);
            doc.text(`${controle.hrAlmocoRetorno}`, 84, y);
            doc.text(`${controle.hrSaida}`, 103, y);
            doc.text(observacao, 120, y);
            y += 7;
        });

        doc.output('dataurlnewwindow');
    }

    private formatData(data: any): string {
        const dia = data.date() < 10 ? '0' + data.date() : data.date();
        const mes = data.month() + 1 < 10 ? '0' + (data.month() + 1) : data.month() + 1;
        return `${dia}/${mes}/${data.year()}`;
    }
}
