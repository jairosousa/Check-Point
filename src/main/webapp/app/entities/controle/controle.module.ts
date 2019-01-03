import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CheckpointSharedModule } from 'app/shared';
import {
    ControleComponent,
    ControleDetailComponent,
    ControleUpdateComponent,
    ControleDeletePopupComponent,
    ControleDeleteDialogComponent,
    controleRoute,
    controlePopupRoute
} from './';

const ENTITY_STATES = [...controleRoute, ...controlePopupRoute];

@NgModule({
    imports: [CheckpointSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ControleComponent,
        ControleDetailComponent,
        ControleUpdateComponent,
        ControleDeleteDialogComponent,
        ControleDeletePopupComponent
    ],
    entryComponents: [ControleComponent, ControleUpdateComponent, ControleDeleteDialogComponent, ControleDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CheckpointControleModule {}
