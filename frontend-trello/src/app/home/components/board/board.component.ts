import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Board } from '../../../types/board';
import { BoardsService } from '../../services/boards.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material/dialog';
import { AddListDialogComponent } from './add-list-dialog/add-list-dialog/add-list-dialog.component';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'tr-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BoardComponent implements OnInit {
  board?: Board;

  editing = false;

  changeTitleForm: FormGroup;

  addListDialogRef: MatDialogRef<AddListDialogComponent> | undefined;

  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private boardService: BoardsService,
    private formBuilder: FormBuilder,
    private matDialog: MatDialog,
    private cdr: ChangeDetectorRef,
  ) {
    this.changeTitleForm = this.formBuilder.group({
      boardTitle: ['', [Validators.required, Validators.pattern(/^[a-zA-Zа-яА-Я0-9\s\-._]*$/)]],
    });
  }

  ngOnInit(): void {
    this.initBoard();
    this.changeTitleForm.get('boardTitle')?.setValue(this.board?.title);
  }

  private initBoard(): void {
    this.activatedRoute.data.subscribe(({ board }) => {
      this.board = board;
    });
  }

  submitChangeTitleForm() {
    if (this.changeTitleForm.valid) {
      this.changeTitle(this.changeTitleForm.value.boardTitle);
      this.editing = false;
    }
  }

  changeTitle(boardTitle: string): void {
    this.activatedRoute.paramMap.subscribe((params) => {
      const id = params.get('id');
      if (this.board != undefined && boardTitle != undefined && id != undefined) {
        this.board.title = boardTitle;
        this.boardService.changeBoard(this.board, id);
        this.boardService.getBoardById(id).subscribe((board) => {
          this.board = board;
        });
      }
    });
    this.editing = false;
  }

  openDialog(addListBtn: MatButton) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    console.log(addListBtn._elementRef.nativeElement.offsetLeft + 'px');
    dialogConfig.position = {
      left:
        addListBtn._elementRef.nativeElement.offsetLeft < window.innerWidth
          ? addListBtn._elementRef.nativeElement.offsetLeft
          : window.innerWidth - addListBtn._elementRef.nativeElement.offsetWidth - 40 + 'px', // TODO fix
      top:
        addListBtn._elementRef.nativeElement.offsetTop +
        addListBtn._elementRef.nativeElement.offsetHeight +
        5 +
        'px',
    };
    const boardId = this.activatedRoute.snapshot.paramMap.get('id');
    dialogConfig.data = { boardId: boardId };
    this.addListDialogRef = this.matDialog.open(AddListDialogComponent, dialogConfig);
    this.addListDialogRef.afterClosed().subscribe(() => {
      this.boardService.getBoardById(boardId).subscribe((board) => {
        this.board = board;
        this.cdr.markForCheck();
      });
    });
  }
}
