import { RouterStateSnapshot, ActivatedRouteSnapshot, ResolveFn } from '@angular/router';
import { Board } from '../../../../types/board';
import { board } from '../../../../data/Boards';

export const boardResolver: ResolveFn<Board> =
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => board;
