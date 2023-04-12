import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { Board } from '../../../types/board';
import { ActivatedRoute } from '@angular/router';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AddNewBoardDialogComponent } from './add-new-board-dialog/add-new-board-dialog.component';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'tr-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HomeComponent implements OnInit {
  boards: Board[] = [];

  constructor(private readonly activatedRoute: ActivatedRoute, private dialog: MatDialog) {}

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
    this.dialog.open(AddNewBoardDialogComponent, dialogConfig);
  }
}
