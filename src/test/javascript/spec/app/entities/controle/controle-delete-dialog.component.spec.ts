/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CheckpointTestModule } from '../../../test.module';
import { ControleDeleteDialogComponent } from 'app/entities/controle/controle-delete-dialog.component';
import { ControleService } from 'app/entities/controle/controle.service';

describe('Component Tests', () => {
    describe('Controle Management Delete Component', () => {
        let comp: ControleDeleteDialogComponent;
        let fixture: ComponentFixture<ControleDeleteDialogComponent>;
        let service: ControleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CheckpointTestModule],
                declarations: [ControleDeleteDialogComponent]
            })
                .overrideTemplate(ControleDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ControleDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ControleService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
