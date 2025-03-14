package fr.m3acnl;

import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Classe principale de Tests.
 * Cette classe est utilisée pour définir des méthodes de test communes à toutes les classes de test.
 *
 * @author PUREN Mewen
 */
@ExtendWith(VerifTest.class)
public abstract class Tests {

    /**
     * Cette méthode est exécutée une fois avant tous les tests de cette classe.
     * Elle appelle la méthode printNameAtStart de la classe parente Tests pour afficher le nom de la classe testée.
     * 
     * @param T la classe testée
     */
    public static void printNameAtStart(Class<? extends Tests> T){
        System.out.println("\n\n\033[31;1m================================ \033[36;1mDÉBUT " + T.getSimpleName() + " \033[31;1m================================\033[0m");
    }

    /**
     * Cette méthode est exécutée à la fin de tous les tests de cette classe.
     * Elle appelle la méthode printNameAtEnd de la classe parente Tests pour afficher le nom de la classe testée.
     * 
     * @param T la classe testée
     */
    public static void printNameAtEnd(Class<? extends Tests> T){
        System.out.println("\033[31;1m================================  \033[36;1mFIN " + T.getSimpleName() + "  \033[31;1m================================\033[0m\n");
    }

    /**
     * Constructeur de la classe.
     */
    public Tests() {}
    
}
