import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Board } from '../../../types/board';
import { board } from '../../../data/Boards';

@Component({
  selector: 'tr-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HomeComponent {
  boards: Board[] = [
    { id: 1, title: 'shopping' },
    { id: 2, title: 'wedding preparation' },
    { id: 3, title: 'development of an online store' },
    { id: 4, title: 'course on promotion in social networks' },
    board,
  ];
}
