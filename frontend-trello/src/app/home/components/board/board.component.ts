import { ChangeDetectionStrategy, Component } from '@angular/core';
import { board } from '../../../data/Boards';
import { List } from '../../../types/list';

@Component({
  selector: 'tr-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BoardComponent {
  title: string = board.title;

  lists: List[] = board.lists;
}
