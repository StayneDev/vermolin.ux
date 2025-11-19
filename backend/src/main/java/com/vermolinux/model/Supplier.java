package com.vermolinux.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidade Supplier - Representa um fornecedor de produtos
 */
@Entity
@Table(name = "suppliers")
public class Supplier {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(unique = true, nullable = false, length = 14)
    private String cnpj;
    
    @Column(name = "contact_name", length = 100)
    private String contactName;
    
    @Column(length = 20)
    private String phone;
    
    @Column(length = 100)
    private String email;
    
    @Column(length = 255)
    private String address;
    
    @Column(nullable = false)
    private Boolean active = true;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    @Column(name = "created_by")
    private Long createdBy;
    
    @Column(name = "updated_by")
    private Long updatedBy;
    
    // Explicit getters and setters for compilation compatibility
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCnpj() {
        return cnpj;
    }
    
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public String getContactName() {
        return contactName;
    }
    
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public Boolean getActive() {
        return active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Long getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    
    public Long getUpdatedBy() {
        return updatedBy;
    }
    
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    // Builder manual para compatibilidade
    public static SupplierBuilder builder() { return new SupplierBuilder(); }
    public static class SupplierBuilder {
        private Supplier i = new Supplier();
        
        public SupplierBuilder id(Long id) { i.setId(id); return this; }
        public SupplierBuilder name(String name) { i.setName(name); return this; }
        public SupplierBuilder cnpj(String cnpj) { i.setCnpj(cnpj); return this; }
        public SupplierBuilder contactName(String contactName) { i.setContactName(contactName); return this; }
        public SupplierBuilder phone(String phone) { i.setPhone(phone); return this; }
        public SupplierBuilder email(String email) { i.setEmail(email); return this; }
        public SupplierBuilder address(String address) { i.setAddress(address); return this; }
        public SupplierBuilder active(Boolean active) { i.setActive(active); return this; }
        public SupplierBuilder createdAt(LocalDateTime createdAt) { i.setCreatedAt(createdAt); return this; }
        public SupplierBuilder updatedAt(LocalDateTime updatedAt) { i.setUpdatedAt(updatedAt); return this; }
        public SupplierBuilder createdBy(Long createdBy) { i.setCreatedBy(createdBy); return this; }
        public SupplierBuilder updatedBy(Long updatedBy) { i.setUpdatedBy(updatedBy); return this; }
        
        public Supplier build() { return i; }
    }
}


