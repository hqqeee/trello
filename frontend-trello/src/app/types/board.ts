import { List } from './list';

export interface Board {
  id: number;
  title: string;
  lists?: List[];
}
