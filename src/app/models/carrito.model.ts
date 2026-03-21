// Modelos TypeScript para el módulo Carrito de Compras

export interface CarritoItemResponse {
  idCarritoItem: number;
  idLibro: number;
  tituloLibro: string;
  cantidad: number;
  precioUnitario: number;
  total: number;
}

export interface CarritoResponse {
  idCarrito: number;
  token: string;
  nombreCliente: string;
  subtotal: number;
  descuento: number;
  impuestos: number;
  total: number;
  actualizadoEn: string;
  items: CarritoItemResponse[];
}

export interface AddItemRequest {
  idLibro: number;
  cantidad: number;
}

export interface UpdateItemRequest {
  cantidad: number;
}

export interface CheckoutRequest {
  idCliente: number;
  metodoPago: string;
}
