import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { map, Observable } from 'rxjs';
import { Board } from '../../types/board';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class BoardsService {
  apiUrl = environment.baseURL;

  boardUrl = this.apiUrl + 'board';

  authToken = environment.authToken;

  constructor(private httpClient: HttpClient) {}

  getBoards(): Observable<Board[]> {
    return this.httpClient
      .get<{ boards: Board[] }>(this.boardUrl, {
        headers: {
          Authorization: this.authToken,
        },
      })
      .pipe(map((response) => response.boards));
  }

  getBoardById(id: string | null): Observable<Board> {
    if (id === null) return new Observable<Board>();
    return this.httpClient
      .get<Board>(`${this.boardUrl}/${id}`, {
        headers: {
          Authorization: this.authToken,
        },
      })
      .pipe(map((response) => response));
  }

  createBoard(board: Board) {
    this.httpClient
      .post<Board>(this.boardUrl, board, {
        headers: {
          Authorization: this.authToken,
        },
      })
      .subscribe();
  }

  changeBoard(board: Board, id: string | null) {
    if (id === null) return new Observable<Board>();
    return this.httpClient
      .put<Board>(`${this.boardUrl}/${id}`, board, {
        headers: {
          Authorization: this.authToken,
        },
      })
      .subscribe((response) => {
        console.log(response);
      });
  }
}
