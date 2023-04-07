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

  private httpClient: HttpClient;

  constructor(httpClient: HttpClient) {
    this.httpClient = httpClient;
  }

  getBoards(): Observable<Board[]> {
    return this.httpClient
      .get<{ boards: Board[] }>(this.boardUrl, {
        headers: {
          Authorization:
            'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbGFzMjFkQGFhZHNkLmFkIiwiaWF0IjoxNjgwODY3NDc1LCJleHAiOjE2ODA4NjgwNzV9.mz22Ir1t9zqayFdyRJKHJKNHJdv-qmSMdeFmRsyOU2kg-s47zCJnxfFUa9XHJyY0rvcPyCOgRo5aiJ4n0qhmJw',
        },
      })
      .pipe(map((response) => response.boards));
  }

  getBoardById(id: string | null): Observable<Board> {
    if (id === null) return new Observable<Board>();
    return this.httpClient
      .get<Board>(`${this.boardUrl}/${id}`, {
        headers: {
          Authorization:
            'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbGFzMjFkQGFhZHNkLmFkIiwiaWF0IjoxNjgwODY3NDc1LCJleHAiOjE2ODA4NjgwNzV9.mz22Ir1t9zqayFdyRJKHJKNHJdv-qmSMdeFmRsyOU2kg-s47zCJnxfFUa9XHJyY0rvcPyCOgRo5aiJ4n0qhmJw',
        },
      })
      .pipe(map((response) => response));
  }
}
