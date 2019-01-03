/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CheckpointTestModule } from '../../../test.module';
import { ControleDetailComponent } from 'app/entities/controle/controle-detail.component';
import { Controle } from 'app/shared/model/controle.model';

describe('Component Tests', () => {
    describe('Controle Management Detail Component', () => {
        let comp: ControleDetailComponent;
        let fixture: ComponentFixture<ControleDetailComponent>;
        const route = ({ data: of({ controle: new Controle(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CheckpointTestModule],
                declarations: [ControleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ControleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ControleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.controle).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
