import {Sector} from './sector';

export class User {
  id: number;
  name: string;
  sectors: Array<Sector>;
  agreeToTerms: boolean
}
