import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Board } from '../../../types/board';
import { BoardsService } from '../../services/boards.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

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

  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private boardService: BoardsService,
    private formBuilder: FormBuilder,
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
}
