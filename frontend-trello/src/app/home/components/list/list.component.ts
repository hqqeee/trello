import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import { Card } from '../../../types/card';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ListService } from '../../services/list.service';
import { CardService } from '../../services/card.service';

@Component({
  selector: 'tr-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListComponent implements OnInit {
  @Input() title = '';

  @Input() id: number | undefined;

  @Input() cards: Card[] = [];

  @Input() boardId: string | null = '';

  @Output() reloadBoard = new EventEmitter();

  addingNewCard = false;

  editing = false;

  changeListTitleForm: FormGroup;

  addNewCardForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private listService: ListService,
    private cardService: CardService,
  ) {
    this.changeListTitleForm = formBuilder.group({
      listTitle: ['', [Validators.required, Validators.pattern(/^[a-zA-Zа-яА-Я0-9\s\-._]*$/)]],
    });
    this.addNewCardForm = formBuilder.group({
      cardTitle: ['', [Validators.required, Validators.pattern(/^[a-zA-Zа-яА-Я0-9\s\-._]*$/)]],
    });
  }

  ngOnInit(): void {
    this.changeListTitleForm.get('listTitle')?.setValue(this.title);
  }

  submitChangeTitleForm() {
    if (this.changeListTitleForm.valid) {
      this.changeTitle(this.changeListTitleForm.value.listTitle);
      this.editing = false;
    }
  }

  private changeTitle(listTitle: string) {
    this.listService
      .editListTitle(listTitle, this.id, this.boardId)
      .subscribe(() => this.reloadBoard.emit());
  }

  submitAddNewCard() {
    if (this.addNewCardForm.valid) {
      this.addNewCard(this.addNewCardForm.value.cardTitle);
      this.addingNewCard = false;
    }
  }

  private addNewCard(cardTitle: string) {
    if (this.boardId && this.id) {
      this.cardService
        .createCard(this.boardId, this.id, cardTitle)
        .subscribe(() => this.reloadBoard.emit());
    }
  }
}
