import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';

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
  ) {}

  ngOnInit() {
    this.form = this.fb.group({
      boardTitle: [this.boardTitle, []],
    });
  }

  save() {
    console.log(this.form?.value);
  }

  close() {
    this.dialogRef.close();
  }
}
