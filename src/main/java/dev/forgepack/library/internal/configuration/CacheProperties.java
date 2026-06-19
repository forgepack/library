package dev.forgepack.library.internal.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * Propriedades de configuração da infraestrutura de cache da biblioteca.
 *
 * <p>Permite que projetos consumidores sobrescrevam os valores padrão de TTL,
 * refresh, capacidade inicial e tamanho máximo via {@code application.properties}
 * sob o prefixo {@code forgepack.cache}.</p>
 *
 * @param ttl             Tempo de expiração após a escrita. Padrão: 1 hora.
 * @param refresh         Tempo para refresh assíncrono antes da expiração. Padrão: 45 minutos.
 * @param initialCapacity Capacidade inicial do cache. Padrão: 100.
 * @param maximumSize     Tamanho máximo de entradas no cache. Padrão: 5000.
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 */
@ConfigurationProperties(prefix = "forgepack.cache")
public record CacheProperties(
        Duration ttl,
        Duration refresh,
        int initialCapacity,
        int maximumSize
) {
    public CacheProperties() {
        this(
                Duration.ofHours(1),
                Duration.ofMinutes(45),
                100,
                5000
        );
    }
}