import { ChangeDetectionStrategy, Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { List } from '../../../../../types/list';
import { ListService } from '../../../../services/list.service';

@Component({
  selector: 'tr-add-list-dialog',
  templateUrl: './add-list-dialog.component.html',
  styleUrls: ['./add-list-dialog.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AddListDialogComponent implements OnInit {
  form!: FormGroup;

  listTitle: string | undefined;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<AddListDialogComponent>,
    private listService: ListService,
    @Inject(MAT_DIALOG_DATA) public data: { boardId: string },
  ) {}

  ngOnInit() {
    this.form = this.fb.group({
      listTitle: [
        this.listTitle,
        [Validators.required, Validators.pattern(/^[a-zA-Zа-яА-Я0-9\s\-._]*$/)],
      ],
    });
  }

  close() {
    this.dialogRef.close();
  }

  save() {
    const list: List = {
      title: this.form.value.listTitle ?? '',
      cards: [],
    };
    this.listService.createList(list, this.data.boardId);
    this.dialogRef.close();
  }
}
