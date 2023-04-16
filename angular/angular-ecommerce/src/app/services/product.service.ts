import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../common/product';
import { Observable } from 'rxjs';
import {map} from 'rxjs'

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private baseUrl: string = 'http://localhost:8080/api/products';

  constructor(private httpClient: HttpClient) {}

  getProducts():any{
    return this.httpClient.get(this.baseUrl);
  }
}
