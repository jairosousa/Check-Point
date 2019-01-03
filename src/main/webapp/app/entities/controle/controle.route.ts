import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Controle } from 'app/shared/model/controle.model';
import { ControleService } from './controle.service';
import { ControleComponent } from './controle.component';
import { ControleDetailComponent } from './controle-detail.component';
import { ControleUpdateComponent } from './controle-update.component';
import { ControleDeletePopupComponent } from './controle-delete-dialog.component';
import { IControle } from 'app/shared/model/controle.model';

@Injectable({ providedIn: 'root' })
export class ControleResolve implements Resolve<IControle> {
    constructor(private service: ControleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Controle> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Controle>) => response.ok),
                map((controle: HttpResponse<Controle>) => controle.body)
            );
        }
        return of(new Controle());
    }
}

export const controleRoute: Routes = [
    {
        path: 'controle',
        component: ControleComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'checkpointApp.controle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'controle/:id/view',
        component: ControleDetailComponent,
        resolve: {
            controle: ControleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'checkpointApp.controle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'controle/new',
        component: ControleUpdateComponent,
        resolve: {
            controle: ControleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'checkpointApp.controle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'controle/:id/edit',
        component: ControleUpdateComponent,
        resolve: {
            controle: ControleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'checkpointApp.controle.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const controlePopupRoute: Routes = [
    {
        path: 'controle/:id/delete',
        component: ControleDeletePopupComponent,
        resolve: {
            controle: ControleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'checkpointApp.controle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
