import { IBasetype } from 'app/shared/model/basetype.model';

export interface IMonster {
  id?: number;
  name?: string;
  creatorId?: number;
  str?: number;
  con?: number;
  dex?: number;
  intl?: number;
  wis?: number;
  cha?: number;
  basetype?: IBasetype;
}

export class Monster implements IMonster {
  constructor(
    public id?: number,
    public name?: string,
    public creatorId?: number,
    public str?: number,
    public con?: number,
    public dex?: number,
    public intl?: number,
    public wis?: number,
    public cha?: number,
    public basetype?: IBasetype
  ) {}
}
