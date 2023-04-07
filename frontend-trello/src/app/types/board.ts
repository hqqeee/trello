import { List } from './list';

export interface Board {
  id?: number;
  title: string;
  custom: any;
  lists?: List[];
}
