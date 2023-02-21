package kovj19.textUI;

import java.util.Scanner;

import kovj19.logic.Game;

/**
 * Třída představující uživatelské rozhraní aplikace. Zajišťuje načítání
 * příkazů z konzole a výpis reakcí hry.
 *
 * @author Jan Říha
 * @version LS-2021, 2021-05-10
 */
public class TextUI
{
    private Game game;
    private Scanner scanner;

    /**
     * Konstruktor třídy, vytvoří uživatelské rozhraní pro danou hru.
     *
     * @param game hra
     */
    public TextUI(Game game)
    {
        this.game = game;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Metoda zajišťuje hraní hry. Nejprve vypíše úvodní text. Poté v cyklu
     * načítá zadané příkazy z konzole, předává je hře ke zpracování a vypisuje
     * reakce hry. To se neustále opakuje, dokud hra prostřednictvím metody
     * {@link Game#isGameOver() isGameOver} neoznámí, že skončila.
     */
    public void play()
    {
        System.out.println(game.getPrologue());

        while(!game.isGameOver()) {
            System.out.print("\n> ");
            String line = scanner.nextLine();

            System.out.println(game.processAction(line));
        }

        System.out.println("\n" + game.getEpilogue());
    }
}
