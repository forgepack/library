package dev.forgepack.library.internal;

import dev.forgepack.library.api.LibraryService;

/**
 * Implementação padrão do serviço da biblioteca ForgePack.
 * <p>
 * Esta classe fornece a implementação concreta da interface {@link LibraryService},
 * executando as operações principais de processamento da biblioteca.
 * 
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 * Website: www.forgepack.dev
 * 
 * @see LibraryService
 */
public class LibraryServiceImpl implements LibraryService {
    
    /**
     * {@inheritDoc}
     * <p>
     * Implementação que executa o processamento padrão da biblioteca,
     * exibindo uma mensagem de confirmação no console.
     */
    @Override
    public void process() {
        System.out.println("Hello Library");
    }
}
