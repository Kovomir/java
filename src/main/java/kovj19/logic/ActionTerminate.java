package kovj19.logic;

/**
 * Třída implementující příkaz pro předčasné ukončení hry.
 *
 * @author Jan Říha
 * @version LS-2021, 2021-05-10
 */
public class ActionTerminate implements IAction
{
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public ActionTerminate(Game game)
    {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu tj.&nbsp; slovo <b>konec</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName()
    {
        return "konec";
    }

    /**
     * Metoda ukončí hru.
     *
     * @param parameters parametry příkazu <i>(aktuálně se ignorují)</i>
     * @return informace pro hráče, které hra vypíše na konzoli
     */
    @Override
    public String execute(String[] parameters)
    {
        game.setGameOver(true);

        return "Hra byla ukončena příkazem KONEC.\n";
    }
}
