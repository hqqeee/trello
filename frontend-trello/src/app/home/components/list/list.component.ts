import { ChangeDetectionStrategy, Component, Input} from '@angular/core';
import { Card } from '../../../types/card';

@Component({
  selector: 'tr-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListComponent {
  @Input() title = '';

  @Input() cards: Card[] = [];
}
