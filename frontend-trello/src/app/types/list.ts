import { Card } from './card';

export interface List {
  id: number;
  title: string;
  cards: Card[];
}
