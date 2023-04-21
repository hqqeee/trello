import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { List } from '../../types/list';

@Injectable({
  providedIn: 'root',
})
export class ListService {
  apiUrl = environment.baseURL;

  boardUrl = this.apiUrl + 'board';

  authToken = environment.authToken;

  constructor(private httpClient: HttpClient) {}

  createList(list: List, boardId: string) {
    this.httpClient
      .post<List>(this.getURL(boardId), list, {
        headers: {
          Authorization: this.authToken,
        },
      })
      .subscribe((response) => {
        console.log(response);
      });
  }

  editListTitle(newTitle: string, listId: number | undefined, boardId: string | null) {
    this.httpClient
      .put(
        this.getListURL(
          boardId ? boardId.toString() : '',
          listId ? listId.toString().toString() : '',
        ),
        {
          title: newTitle,
          position: 1, // TODO
        },
        {
          headers: {
            Authorization: this.authToken,
          },
        },
      )
      .subscribe((response) => {
        console.log(response);
      });
  }

  private getURL(boardId: string): string {
    return `${this.boardUrl}/${boardId}/list`;
  }

  private getListURL(boardId: string, listId: string) {
    return this.getURL(boardId) + `/${listId}`;
  }
}
