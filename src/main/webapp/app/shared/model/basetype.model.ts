export interface IBasetype {
  id?: number;
  name?: string;
  description?: string;
}

export class Basetype implements IBasetype {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
