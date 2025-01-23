package fr.m3acnl;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

/**
 * Classe de supervision des tests.
 * Cette classe implémente l'interface TestWatcher qui est utilisée pour définir des méthodes de rappel
 * qui seront appelées lorsqu'un test est réussi, échoué, désactivé ou abandonné.
 * 
 * @author PUREN Mewen
 */
public class VerifTest implements TestWatcher {

    /**
     * Cette méthode est appelée lorsque le test est réussi.
     * Elle affiche le nom du test et le statut "OK" en vert.
     */
    @Override
    public void testSuccessful(ExtensionContext context) {
        System.out.println("[\033[34;1m" + context.getDisplayName() + "\033[0m] \033[32;1m\tOK\033[0m");
    }

    /**
     * Cette méthode est appelée lorsque le test échoue.
     * Elle affiche le nom du test et le statut "KO" en rouge.
     */
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        System.out.println("[\033[34;1m" + context.getDisplayName() + "\033[0m] \033[31;1m\tKO\033[0m");
    }

    /**
     * Cette méthode est appelée lorsque le test est désactivé.
     * Elle affiche le nom du test et le statut "DÉSACTIVÉ" en jaune.
     */
    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        System.out.println("[\033[34;1m" + context.getDisplayName() + "\033[0m] \033[33;1m\tDÉSACTIVÉ\033[0m");
    }

    /**
     * Cette méthode est appelée lorsque le test est abandonné.
     * Elle affiche le nom du test et le statut "AVORTÉ" en jaune.
     */
    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        System.out.println("[\033[34;1m" + context.getDisplayName() + "\033[0m] \033[33;1m\tAVORTÉ\033[0m");
    }
}