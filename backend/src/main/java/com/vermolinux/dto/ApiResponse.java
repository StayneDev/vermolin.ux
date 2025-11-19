package com.vermolinux.dto;

/**
 * DTO genérico para respostas de API
 */
public class ApiResponse<T> {
    
    private Boolean success;
    private String message;
    private T data;
    
    public Boolean getSuccess() {
        return success;
    }
    
    public void setSuccess(Boolean success) {
        this.success = success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .build();
    }
    
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }
    
    public static <T> ApiResponse<T> error(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }
    
    public static <T> ApiResponseBuilder<T> builder() { return new ApiResponseBuilder<>(); }
    public static class ApiResponseBuilder<T> {
        private ApiResponse<T> i = new ApiResponse<>();
        public ApiResponseBuilder<T> success(Boolean success) { i.setSuccess(success); return this; }
        public ApiResponseBuilder<T> message(String message) { i.setMessage(message); return this; }
        public ApiResponseBuilder<T> data(T data) { i.setData(data); return this; }
        public ApiResponse<T> build() { return i; }
    }
}


