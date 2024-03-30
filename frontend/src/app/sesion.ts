
export interface Sesion { 
    idPlan: number;
    inicio: Date;
    fin: Date;
    trabajoRealizado: string;
    multimedia: string[];
    descripcion: string;
    presencial: boolean;
    datosSalud: string[];
    id: number;
}