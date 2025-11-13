export type UserRole = 'GERENTE' | 'ESTOQUISTA' | 'CAIXA';

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  userId: number;
  username: string;
  fullName: string;
  role: UserRole;
}

export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
}

export interface Product {
  id: number;
  code: string;
  name: string;
  description: string;
  price: number;
  stockQuantity: number;
  unit: string;
  minStock?: number | null;
  supplierId?: number | null;
  supplierName?: string | null;
  requiresWeighing?: boolean;
  lowStock?: boolean;
  active: boolean;
}

export type SaleStatus = 'OPEN' | 'PAID' | 'CANCELLED';
export type PaymentMethod = 'DINHEIRO' | 'CARTAO' | 'PIX';

export interface Sale {
  id: number;
  cashierId: number;
  cashierName: string;
  items: SaleItem[];
  totalAmount: number;
  status: SaleStatus;
  paymentMethod?: PaymentMethod | null;
  amountPaid?: number;
  changeAmount?: number;
  createdAt: string;
  paidAt?: string;
  cancelledAt?: string;
  cancellationReason?: string;
}

export interface SaleItem {
  id: number;
  productId: number;
  productName: string;
  productPrice: number;
  quantity: number;
  unit: string;
  subtotal: number;
  weighed?: boolean;
}

export type MovementType = 'ENTRADA' | 'SAIDA' | 'AJUSTE' | 'VENDA';
export type MovementReason = 'COMPRA' | 'VENDA' | 'PERDA' | 'DOACAO' | 'DEVOLUCAO' | 'AJUSTE' | 'OUTROS';

export interface StockMovement {
  id: number;
  productId: number;
  productName: string;
  movementType: MovementType;
  quantity: number;
  previousQuantity: number;
  newQuantity: number;
  reason: MovementReason | null;
  notes?: string;
  supplierId?: number | null;
  supplierName?: string | null;
  expiryDate?: string | null;
  createdAt: string;
  createdBy: number;
  createdByName: string | null;
  saleId?: number | null;
}

export interface StockMovementPayload {
  productId: number;
  quantity: number;
  movementType?: MovementType;
  reason?: MovementReason;
  notes?: string;
  supplierId?: number;
  expiryDate?: string;
}

export interface Supplier {
  id: number;
  name: string;
  cnpj: string;
  contactName?: string | null;
  phone?: string | null;
  email?: string | null;
  address?: string | null;
  active: boolean;
  createdAt: string;
  updatedAt?: string;
}

export interface SupplierPayload {
  name: string;
  cnpj: string;
  contactName?: string;
  phone?: string;
  email?: string;
  address?: string;
  active?: boolean;
}

export interface User {
  id: number;
  username: string;
  fullName: string;
  role: UserRole;
  active: boolean;
  createdAt: string;
  updatedAt?: string;
}

export interface UserPayload {
  username: string;
  password?: string;
  fullName: string;
  role: UserRole;
  active?: boolean;
}
