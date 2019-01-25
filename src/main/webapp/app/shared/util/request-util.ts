import { HttpParams } from '@angular/common/http';
import { ControleFiltro } from 'app/entities/controle';

export const createRequestOption = (req?: any, filtro?: ControleFiltro): HttpParams => {
    let options: HttpParams = new HttpParams();
    if (filtro.dataVencimentoInicio) {
        options = options.set('dataVencimentoDe', filtro.dataVencimentoInicio.format('YYYY-MM-DD'));
    }

    if (filtro.dataVencimentoFim) {
        options = options.set('dataVencimentoAte', filtro.dataVencimentoFim.format('YYYY-MM-DD'));
    }
    if (req) {
        Object.keys(req).forEach(key => {
            if (key !== 'sort') {
                options = options.set(key, req[key]);
            }
        });
        if (req.sort) {
            req.sort.forEach(val => {
                options = options.append('sort', val);
            });
        }
    }
    return options;
};
