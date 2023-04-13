import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { BoardsService } from '../../../services/boards.service';
import { Board } from '../../../../types/board';

@Component({
  selector: 'tr-add-new-board-dialog',
  templateUrl: './add-new-board-dialog.component.html',
  styleUrls: ['./add-new-board-dialog.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AddNewBoardDialogComponent implements OnInit {
  form!: FormGroup;

  boardTitle: string | undefined;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<AddNewBoardDialogComponent>,
    private boardService: BoardsService,
  ) {}

  ngOnInit() {
    this.form = this.fb.group({
      boardTitle: [
        this.boardTitle,
        [Validators.required, Validators.pattern(/^[a-zA-Zа-яА-Я0-9\s\-._]*$/)],
      ],
    });
  }

  save() {
    const board: Board = {
      title: this.form.value.boardTitle ?? '',
      custom: '',
    };
    this.boardService.createBoard(board);
    this.dialogRef.close(board);
  }

  close() {
    this.dialogRef.close();
  }
}
