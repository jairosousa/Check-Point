import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { Moment } from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IControle } from 'app/shared/model/controle.model';

type EntityResponseType = HttpResponse<IControle>;
type EntityArrayResponseType = HttpResponse<IControle[]>;

export class ControleFiltro {
    dataVencimentoInicio: Moment;
    dataVencimentoFim: Moment;
}

@Injectable({ providedIn: 'root' })
export class ControleService {
    public resourceUrl = SERVER_API_URL + 'api/controles';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/controles';

    constructor(protected http: HttpClient) {}

    create(controle: IControle): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(controle);
        return this.http
            .post<IControle>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(controle: IControle): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(controle);
        return this.http
            .put<IControle>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IControle>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any, filtro?: ControleFiltro): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req, filtro);
        return this.http
            .get<IControle[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IControle[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(controle: IControle): IControle {
        const copy: IControle = Object.assign({}, controle, {
            data: controle.data != null && controle.data.isValid() ? controle.data.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.data = res.body.data != null ? moment(res.body.data) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((controle: IControle) => {
                controle.data = controle.data != null ? moment(controle.data) : null;
            });
        }
        return res;
    }
}
