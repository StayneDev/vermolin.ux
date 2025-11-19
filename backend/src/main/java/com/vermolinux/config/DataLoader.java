package com.vermolinux.config;

import org.springframework.stereotype.Component;

/**
 * DataLoader desabilitado - Flyway está cuidando das migrações de banco de dados.
 * Em produção com PostgreSQL, todas as migrações e carregamento de dados iniciais 
 * são executados através de scripts SQL (V*.sql) no diretório db/migration/.
 * 
 * Anteriormente usado para popular dados em memória com H2.
 */
@Component
public class DataLoader {
    // Dados iniciais agora são gerenciados via Flyway migrations (V7__insert_initial_data.sql)
}
