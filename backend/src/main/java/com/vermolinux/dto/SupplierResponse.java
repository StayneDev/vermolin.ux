package com.vermolinux.dto;

import java.time.LocalDateTime;

/**
 * DTO para resposta com dados de fornecedor (RF31)
 */
public class SupplierResponse {
    
    private Long id;
    private String name;
    private String cnpj;
    private String contactName;
    private String phone;
    private String email;
    private String address;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public SupplierResponse() {}
    
    public SupplierResponse(Long id, String name, String cnpj, String contactName, 
                           String phone, String email, String address, Boolean active, 
                           LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.cnpj = cnpj;
        this.contactName = contactName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getCnpj() {
        return cnpj;
    }
    
    public String getContactName() {
        return contactName;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getAddress() {
        return address;
    }
    
    public Boolean getActive() {
        return active;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Builder
    public static SupplierResponseBuilder builder() {
        return new SupplierResponseBuilder();
    }
    
    public static class SupplierResponseBuilder {
        private Long id;
        private String name;
        private String cnpj;
        private String contactName;
        private String phone;
        private String email;
        private String address;
        private Boolean active;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        
        public SupplierResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }
        
        public SupplierResponseBuilder name(String name) {
            this.name = name;
            return this;
        }
        
        public SupplierResponseBuilder cnpj(String cnpj) {
            this.cnpj = cnpj;
            return this;
        }
        
        public SupplierResponseBuilder contactName(String contactName) {
            this.contactName = contactName;
            return this;
        }
        
        public SupplierResponseBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }
        
        public SupplierResponseBuilder email(String email) {
            this.email = email;
            return this;
        }
        
        public SupplierResponseBuilder address(String address) {
            this.address = address;
            return this;
        }
        
        public SupplierResponseBuilder active(Boolean active) {
            this.active = active;
            return this;
        }
        
        public SupplierResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        
        public SupplierResponseBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }
        
        public SupplierResponse build() {
            return new SupplierResponse(id, name, cnpj, contactName, phone, email, 
                                       address, active, createdAt, updatedAt);
        }
    }
}


