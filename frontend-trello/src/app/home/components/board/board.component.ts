import { ChangeDetectionStrategy, Component } from '@angular/core';
import { board } from '../../../data/Boards';

@Component({
  selector: 'tr-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BoardComponent {
  board = board;
}
