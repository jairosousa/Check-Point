import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { CheckpointSharedLibsModule, CheckpointSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import { NgbDateCustomParserFormatter } from 'app/shared/util/NgbDateCustomParserFormatter';

@NgModule({
    imports: [CheckpointSharedLibsModule, CheckpointSharedCommonModule],
    declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
    providers: [
        { provide: NgbDateAdapter, useClass: NgbDateMomentAdapter },
        { provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter }
    ],
    entryComponents: [JhiLoginModalComponent],
    exports: [CheckpointSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CheckpointSharedModule {
    static forRoot() {
        return {
            ngModule: CheckpointSharedModule
        };
    }
}
