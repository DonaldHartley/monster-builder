export interface IBasetype {
  id?: number;
  name?: string;
}

export class Basetype implements IBasetype {
  constructor(public id?: number, public name?: string) {}
}
