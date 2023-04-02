import { Injectable } from '@angular/core';
import { Resolve, RouterStateSnapshot, ActivatedRouteSnapshot } from '@angular/router';
import { Observable, of } from 'rxjs';
import { Board } from '../../../../types/board';
import { board } from '../../../../data/Boards';

@Injectable({
  providedIn: 'root',
})
export class BoardResolver implements Resolve<Board> {
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Board> {
    return of(board);
  }
}
