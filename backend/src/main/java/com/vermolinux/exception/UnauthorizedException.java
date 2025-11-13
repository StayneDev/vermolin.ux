package com.vermolinux.exception;

/**
 * Exceção lançada quando há tentativa de operação não autorizada
 */
public class UnauthorizedException extends RuntimeException {
    
    public UnauthorizedException(String message) {
        super(message);
    }
}
