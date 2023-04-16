export class Product {
  constructor(
    public id:string,
    public name: string,
    public descrption: string,
    public sku: string,
    public unitPrice: string,
    public imageUrl: string,
    public active: boolean,
    public unitsInStock: number,
    public createdDate:Date,
    public lastUpdatedDate:Date) {}
}
