package dev.forgepack.library.api;

/**
 * Interface principal do serviço da biblioteca ForgePack.
 * <p>
 * Esta interface define o contrato para os serviços principais da biblioteca,
 * fornecendo operações básicas de processamento que podem ser implementadas
 * por diferentes classes de serviço.
 * 
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 * Website: www.forgepack.dev
 */
public interface LibraryService {
    
    /**
     * Executa o processamento principal do serviço.
     * <p>
     * Este método implementa a lógica principal de processamento da biblioteca,
     * podendo ser personalizado conforme a implementação específica.
     */
    void process();
}
