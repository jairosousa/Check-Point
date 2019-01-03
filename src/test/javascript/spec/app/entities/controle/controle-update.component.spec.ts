/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CheckpointTestModule } from '../../../test.module';
import { ControleUpdateComponent } from 'app/entities/controle/controle-update.component';
import { ControleService } from 'app/entities/controle/controle.service';
import { Controle } from 'app/shared/model/controle.model';

describe('Component Tests', () => {
    describe('Controle Management Update Component', () => {
        let comp: ControleUpdateComponent;
        let fixture: ComponentFixture<ControleUpdateComponent>;
        let service: ControleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CheckpointTestModule],
                declarations: [ControleUpdateComponent]
            })
                .overrideTemplate(ControleUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ControleUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ControleService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Controle(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.controle = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Controle();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.controle = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
