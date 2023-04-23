import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class CardService {
  private apiUrl = environment.baseURL;

  private boardUrl = this.apiUrl + 'board';

  private authToken = environment.authToken;

  constructor(private httpClient: HttpClient) {}

  createCard(boardId: string, listId: number, cardTitle: string) {
    return this.httpClient.post(
      this.getCardURL(boardId),
      {
        title: cardTitle,
        position: 1, // todo
        description: '',
        list_id: listId,
        custom: {},
      },
      {
        headers: {
          Authorization: this.authToken,
        },
      },
    );
  }

  private getCardURL(boardId: string | null) {
    return `${this.boardUrl}/${boardId}/card`;
  }
}
