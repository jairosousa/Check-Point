import { Moment } from 'moment';

export interface IControle {
    id?: number;
    data?: Moment;
    hrEntrada?: string;
    hrAlmocoSaida?: string;
    hrAlmocoRetorno?: string;
    hrSaida?: string;
    corPulseira?: string;
}

export class Controle implements IControle {
    constructor(
        public id?: number,
        public data?: Moment,
        public hrEntrada?: string,
        public hrAlmocoSaida?: string,
        public hrAlmocoRetorno?: string,
        public hrSaida?: string,
        public corPulseira?: string
    ) {}
}
