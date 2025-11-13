package com.vermolinux.exception;

/**
 * Exceção lançada quando há violação de regras de negócio
 */
public class BusinessException extends RuntimeException {
    
    public BusinessException(String message) {
        super(message);
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
