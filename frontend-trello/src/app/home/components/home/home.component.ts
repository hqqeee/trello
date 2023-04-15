import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Board } from '../../../types/board';
import { ActivatedRoute } from '@angular/router';
import { MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material/dialog';
import { AddNewBoardDialogComponent } from './add-new-board-dialog/add-new-board-dialog.component';
import { MatButton } from '@angular/material/button';
import { BoardsService } from '../../services/boards.service';

@Component({
  selector: 'tr-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HomeComponent implements OnInit {
  boards: Board[] = [];

  dialogRef: MatDialogRef<AddNewBoardDialogComponent> | undefined;

  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private dialog: MatDialog,
    private boardService: BoardsService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.initBoards();
  }

  private initBoards(): void {
    this.activatedRoute.data.subscribe(({ boards }) => {
      this.boards = boards;
    });
  }

  openDialog(addBoardBtn: MatButton) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.position = {
      left:
        addBoardBtn._elementRef.nativeElement.offsetLeft +
        addBoardBtn._elementRef.nativeElement.offsetWidth +
        5 +
        'px',
      top: addBoardBtn._elementRef.nativeElement.offsetTop + 'px',
    };
    this.dialogRef = this.dialog.open(AddNewBoardDialogComponent, dialogConfig);
    this.dialogRef.afterClosed().subscribe((newBoard: Board) => {
      if (newBoard) {
        this.boardService.getBoards().subscribe((boards) => {
          this.boards = boards;
          this.cdr.markForCheck();
        });
      }
    });
  }
}
