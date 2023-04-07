import { ActivatedRouteSnapshot, ResolveFn } from '@angular/router';
import { Board } from '../../../../types/board';
import { inject } from '@angular/core';
import { BoardsService } from '../../../services/boards.service';

export const boardResolver: ResolveFn<Board> = (route: ActivatedRouteSnapshot) =>
  inject(BoardsService).getBoardById(route.paramMap.get('id'));

export const boardsResolver: ResolveFn<Board[]> = () => inject(BoardsService).getBoards();
