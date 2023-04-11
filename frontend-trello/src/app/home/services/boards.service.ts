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

  private httpClient: HttpClient;

  constructor(httpClient: HttpClient) {
    this.httpClient = httpClient;
  }

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
}
