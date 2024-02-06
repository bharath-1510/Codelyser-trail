import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ExplorerItem } from '../model/explorer-item.model';

@Injectable({
  providedIn: 'root',
})
export class ExplorerService {
  private apiUrl = 'http://localhost:8080/api/explorer';

  constructor(private http: HttpClient) {}

  getExplorerData(
    id: number,
    type: string,
    email: string
  ): Observable<ExplorerItem[]> {
    if (type === 'Admin') {
      const params = new HttpParams().set('id', id);

      return this.http.get<ExplorerItem[]>(this.apiUrl, { params });
    } else {
      const params = new HttpParams().set('id', id).set('email', email);
      return this.http.get<ExplorerItem[]>(`${this.apiUrl}/test`, { params });
    }
  }
  getFileContent(absolutePath: string): Observable<{ content: string }> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const options = { headers: headers };

    const requestData = { absolutePath: absolutePath };

    return this.http.post<{ content: string }>(
      `${this.apiUrl}/file-content`,
      requestData,
      options
    );
  }
}
