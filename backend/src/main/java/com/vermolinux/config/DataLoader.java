package com.vermolinux.config;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.vermolinux.model.*;
import com.vermolinux.repository.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataLoader {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockMovementRepository stockMovementRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SaleItemRepository saleItemRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void loadData() {
        try {
            // Carregar dados iniciais apenas se estiver vazio
            if (userRepository.count() == 0) {
                loadUsers();
                loadSuppliers();
                loadProducts();
                loadStockMovements();
                loadSales();
                
                System.out.println("✓ Todos os dados iniciais carregados com sucesso!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados iniciais: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        // Criar usuário gerente
        User gerente = new User();
        gerente.setUsername("gerente");
        gerente.setPassword(passwordEncoder.encode("gerente123"));
        gerente.setFullName("Gerente Sistema");
        gerente.setRole(User.UserRole.GERENTE);
        gerente.setActive(true);
        userRepository.save(gerente);

        // Criar usuário estoquista
        User estoquista = new User();
        estoquista.setUsername("estoquista");
        estoquista.setPassword(passwordEncoder.encode("estoquista123"));
        estoquista.setFullName("Estoquista Sistema");
        estoquista.setRole(User.UserRole.ESTOQUISTA);
        estoquista.setActive(true);
        userRepository.save(estoquista);

        // Criar usuário caixa
        User caixa = new User();
        caixa.setUsername("caixa");
        caixa.setPassword(passwordEncoder.encode("caixa123"));
        caixa.setFullName("Caixa Sistema");
        caixa.setRole(User.UserRole.CAIXA);
        caixa.setActive(true);
        userRepository.save(caixa);

        System.out.println("✓ Usuários carregados:");
        System.out.println("  - Gerente: gerente / gerente123");
        System.out.println("  - Estoquista: estoquista / estoquista123");
        System.out.println("  - Caixa: caixa / caixa123");
    }

    private void loadSuppliers() {
        // Fornecedor 1
        Supplier sup1 = new Supplier();
        sup1.setName("Agrícola Verde Ltda");
        sup1.setCnpj("12345678000191");
        sup1.setContactName("João Silva");
        sup1.setPhone("(11) 99999-0001");
        sup1.setEmail("contato@agricolaverde.com.br");
        sup1.setAddress("Rua das Flores, 123 - São Paulo, SP");
        sup1.setActive(true);
        sup1.setCreatedBy(1L);
        supplierRepository.save(sup1);

        // Fornecedor 2
        Supplier sup2 = new Supplier();
        sup2.setName("Produtos Organicos Brasil");
        sup2.setCnpj("98765432000182");
        sup2.setContactName("Maria Santos");
        sup2.setPhone("(11) 99999-0002");
        sup2.setEmail("vendas@organicos.com.br");
        sup2.setAddress("Av. Paulista, 1000 - São Paulo, SP");
        sup2.setActive(true);
        sup2.setCreatedBy(1L);
        supplierRepository.save(sup2);

        // Fornecedor 3
        Supplier sup3 = new Supplier();
        sup3.setName("Horticultura Premium");
        sup3.setCnpj("11111111000111");
        sup3.setContactName("Pedro Costa");
        sup3.setPhone("(11) 99999-0003");
        sup3.setEmail("info@horticulturapremium.com.br");
        sup3.setAddress("Estrada do Campo, 500 - Mogi das Cruzes, SP");
        sup3.setActive(true);
        sup3.setCreatedBy(1L);
        supplierRepository.save(sup3);

        System.out.println("✓ Fornecedores carregados: 3 fornecedores");
    }

    private void loadProducts() {
        User estoquista = userRepository.findByUsername("estoquista").orElse(null);
        Long userId = estoquista != null ? estoquista.getId() : 1L;

        // Produtos de folhas
        createProduct("P001", "Alface", "Alface fresca verde", new BigDecimal("5.90"), Product.ProductUnit.KG, 
                     new BigDecimal("50"), new BigDecimal("10"), 1L, LocalDate.now().plusDays(7), false, userId);
        createProduct("P002", "Rúcula", "Rúcula hidropônica", new BigDecimal("8.50"), Product.ProductUnit.KG,
                     new BigDecimal("30"), new BigDecimal("5"), 1L, LocalDate.now().plusDays(5), false, userId);
        createProduct("P003", "Espinafre", "Espinafre fresco", new BigDecimal("7.20"), Product.ProductUnit.KG,
                     new BigDecimal("25"), new BigDecimal("5"), 1L, LocalDate.now().plusDays(6), false, userId);

        // Produtos de frutas
        createProduct("P004", "Tomate", "Tomate caqui vermelho", new BigDecimal("6.50"), Product.ProductUnit.KG,
                     new BigDecimal("100"), new BigDecimal("20"), 2L, LocalDate.now().plusDays(10), true, userId);
        createProduct("P005", "Maçã", "Maçã vermelha importada", new BigDecimal("12.00"), Product.ProductUnit.KG,
                     new BigDecimal("80"), new BigDecimal("15"), 3L, LocalDate.now().plusDays(30), true, userId);
        createProduct("P006", "Banana", "Banana nanica", new BigDecimal("4.50"), Product.ProductUnit.KG,
                     new BigDecimal("120"), new BigDecimal("30"), 2L, LocalDate.now().plusDays(8), true, userId);

        // Produtos de raízes
        createProduct("P007", "Cenoura", "Cenoura fresca", new BigDecimal("3.80"), Product.ProductUnit.KG,
                     new BigDecimal("60"), new BigDecimal("15"), 1L, LocalDate.now().plusDays(15), true, userId);
        createProduct("P008", "Batata", "Batata inglesa", new BigDecimal("4.20"), Product.ProductUnit.KG,
                     new BigDecimal("200"), new BigDecimal("50"), 3L, LocalDate.now().plusDays(20), true, userId);
        createProduct("P009", "Cebola", "Cebola branca", new BigDecimal("3.50"), Product.ProductUnit.KG,
                     new BigDecimal("150"), new BigDecimal("30"), 1L, LocalDate.now().plusDays(25), true, userId);

        // Outros
        createProduct("P010", "Brócolis", "Brócolis fresco", new BigDecimal("9.90"), Product.ProductUnit.UNIDADE,
                     new BigDecimal("40"), new BigDecimal("8"), 2L, LocalDate.now().plusDays(5), true, userId);

        System.out.println("✓ Produtos carregados: 10 produtos");
    }

    private void createProduct(String code, String name, String description, BigDecimal price, 
                              Product.ProductUnit unit, BigDecimal stock, BigDecimal minStock, 
                              Long supplierId, LocalDate expiryDate, Boolean requiresWeighing, Long createdBy) {
        Product product = new Product();
        product.setCode(code);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setUnit(unit);
        product.setStockQuantity(stock);
        product.setMinStock(minStock);
        product.setSupplierId(supplierId);
        product.setExpiryDate(expiryDate);
        product.setRequiresWeighing(requiresWeighing);
        product.setActive(true);
        product.setCreatedBy(createdBy);
        productRepository.save(product);
    }

    private void loadStockMovements() {
        User estoquista = userRepository.findByUsername("estoquista").orElse(null);
        Long userId = estoquista != null ? estoquista.getId() : 1L;

        // Entradas iniciais de estoque
        for (long productId = 1L; productId <= 10L; productId++) {
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                StockMovement movement = new StockMovement();
                movement.setProductId(productId);
                movement.setMovementType(StockMovement.MovementType.ENTRADA);
                movement.setQuantity(product.getStockQuantity());
                movement.setPreviousQuantity(BigDecimal.ZERO);
                movement.setNewQuantity(product.getStockQuantity());
                movement.setReason("Entrada inicial de estoque");
                movement.setNotes("Carga inicial - Sistema");
                movement.setSupplierId(product.getSupplierId());
                movement.setCreatedBy(userId);
                stockMovementRepository.save(movement);
            }
        }

        // Alguns ajustes simulando movimentação
        createStockMovement(1L, StockMovement.MovementType.SAIDA, new BigDecimal("5"), 
                           new BigDecimal("50"), new BigDecimal("45"), "Ajuste de qualidade", userId);
        createStockMovement(4L, StockMovement.MovementType.AJUSTE, new BigDecimal("10"),
                           new BigDecimal("100"), new BigDecimal("110"), "Recontagem de estoque", userId);

        System.out.println("✓ Movimentações de estoque carregadas");
    }

    private void createStockMovement(Long productId, StockMovement.MovementType type, BigDecimal quantity,
                                     BigDecimal previousQty, BigDecimal newQty, String reason, Long createdBy) {
        StockMovement movement = new StockMovement();
        movement.setProductId(productId);
        movement.setMovementType(type);
        movement.setQuantity(quantity);
        movement.setPreviousQuantity(previousQty);
        movement.setNewQuantity(newQty);
        movement.setReason(reason);
        movement.setCreatedBy(createdBy);
        stockMovementRepository.save(movement);
    }

    private void loadSales() {
        User caixa = userRepository.findByUsername("caixa").orElse(null);
        Long cashierId = caixa != null ? caixa.getId() : 1L;

        // Criar uma venda de exemplo
        Sale sale = new Sale();
        sale.setCashierId(cashierId);
        sale.setPaymentMethod(Sale.PaymentMethod.DINHEIRO);
        sale.setStatus(Sale.SaleStatus.PAID);
        sale.setTotalAmount(BigDecimal.ZERO);
        saleRepository.save(sale);

        // Depois criar os itens com o saleId
        BigDecimal totalAmount = BigDecimal.ZERO;

        // Adicionar itens à venda
        SaleItem item1 = new SaleItem();
        item1.setSaleId(sale.getId());
        item1.setProductId(1L);
        item1.setProductName("Alface");
        item1.setProductPrice(new BigDecimal("5.90"));
        item1.setQuantity(new BigDecimal("2.5"));
        item1.setUnit("KG");
        item1.setSubtotal(new BigDecimal("14.75"));
        saleItemRepository.save(item1);
        totalAmount = totalAmount.add(item1.getSubtotal());

        SaleItem item2 = new SaleItem();
        item2.setSaleId(sale.getId());
        item2.setProductId(4L);
        item2.setProductName("Tomate");
        item2.setProductPrice(new BigDecimal("6.50"));
        item2.setQuantity(new BigDecimal("3"));
        item2.setUnit("KG");
        item2.setSubtotal(new BigDecimal("19.50"));
        saleItemRepository.save(item2);
        totalAmount = totalAmount.add(item2.getSubtotal());

        SaleItem item3 = new SaleItem();
        item3.setSaleId(sale.getId());
        item3.setProductId(7L);
        item3.setProductName("Cenoura");
        item3.setProductPrice(new BigDecimal("3.80"));
        item3.setQuantity(new BigDecimal("5"));
        item3.setUnit("KG");
        item3.setSubtotal(new BigDecimal("19.00"));
        saleItemRepository.save(item3);
        totalAmount = totalAmount.add(item3.getSubtotal());

        // Atualizar totais da venda
        sale.setTotalAmount(totalAmount);
        sale.setAmountPaid(totalAmount);
        sale.setChangeAmount(BigDecimal.ZERO);
        sale.setPaidAt(LocalDateTime.now());
        saleRepository.save(sale);

        System.out.println("✓ Histórico de vendas carregado: 1 venda com 3 itens");
    }
}
