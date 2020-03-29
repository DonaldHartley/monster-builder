import { IBasetype } from 'app/shared/model/basetype.model';

export interface IMonster {
  id?: number;
  name?: string;
  creatorId?: number;
  basetype?: IBasetype;
}

export class Monster implements IMonster {
  constructor(public id?: number, public name?: string, public creatorId?: number, public basetype?: IBasetype) {}
}
