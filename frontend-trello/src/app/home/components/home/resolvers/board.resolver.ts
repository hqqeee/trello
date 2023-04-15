import { ActivatedRouteSnapshot, ResolveFn } from '@angular/router';
import { Board } from '../../../../types/board';
import { inject } from '@angular/core';
import { BoardsService } from '../../../services/boards.service';
import { tap } from 'rxjs';

export const boardResolver: ResolveFn<Board> = (route: ActivatedRouteSnapshot) =>
  inject(BoardsService).getBoardById(route.paramMap.get('id'));

export const boardsResolver: ResolveFn<Board[]> = () => {
  console.log('fetching boards...');
  return inject(BoardsService)
    .getBoards()
    .pipe(tap((boards) => console.log('fetched boards:', boards)));
};
