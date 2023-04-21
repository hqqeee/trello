import { ChangeDetectionStrategy, Component, Input, OnInit } from '@angular/core';
import { Card } from '../../../types/card';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ListService } from '../../services/list.service';

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

  editing = false;

  changeListTitleForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private listService: ListService) {
    this.changeListTitleForm = formBuilder.group({
      listTitle: ['', [Validators.required, Validators.pattern(/^[a-zA-Zа-яА-Я0-9\s\-._]*$/)]],
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
    this.listService.editListTitle(listTitle, this.id, this.boardId);
  }
}
