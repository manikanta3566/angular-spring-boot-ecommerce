import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/common/product';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  baseUrl: string = 'http://localhost:8080/api/products';

   products:Product[]=[];

  constructor(private productService:ProductService) { }

  ngOnInit(): void {
   this.getAllProducts();
  }

  getAllProducts(){
   this.productService.getProducts().subscribe(
      (data:any)=>{
        console.log(data); 
        this.products=data;
      }
    )
  }
}
