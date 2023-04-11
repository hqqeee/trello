import { ChangeDetectionStrategy, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Board } from '../../../types/board';
import { ActivatedRoute } from '@angular/router';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AddNewBoardDialogComponent } from './add-new-board-dialog/add-new-board-dialog.component';

@Component({
  selector: 'tr-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HomeComponent implements OnInit {
  boards: Board[] = [];

  @ViewChild('addBoardBtn') addBtn!: ElementRef<HTMLButtonElement>;

  constructor(private readonly activatedRoute: ActivatedRoute, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.initBoards();
  }

  private initBoards(): void {
    this.activatedRoute.data.subscribe(({ boards }) => {
      this.boards = boards;
    });
  }

  openDialog() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.position = {
      right: this.addBtn.nativeElement.offsetLeft + this.addBtn.nativeElement.offsetWidth + 'px',
      top: this.addBtn.nativeElement.offsetTop + 'px',
    };
    this.dialog.open(AddNewBoardDialogComponent, dialogConfig);
  }
}
