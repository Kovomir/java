package kovj19.logic;

/**
 * Třída implementující příkaz pro průzkum aktuální lokace.
 *
 * @author Jan Říha, Jan Kovář
 * @version LS-2021
 */
public class ActionLookAround implements IAction
{
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public ActionLookAround(Game game)
    {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu tj.&nbsp; slovo <b>rozhledni_se</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName()
    {
        return "rozhlédni_se";
    }

    /**
     * Metoda vrátí detailní popis aktuální lokace.
     *
     * @param parameters parametry příkazu <i>(očekává se prázdné pole)</i>
     * @return informace pro hráče, které hra vypíše na konzoli
     */
    @Override
    public String execute(String[] parameters)
    {
        if (parameters.length > 0) {
            return "Tomu nerozumím, umím se rozhlédnout jen kolem sebe, ne na něco konkrétního.";
        }

        Area currentArea = game.getWorld().getCurrentArea();
        if (!game.getWorld().getInventory().getItemList().isEmpty()) {
            String heldItem = game.getWorld().getInventory().getHeldItem().getName();
            return currentArea.getFullDescription() + "\nV invetáři držíš předmět: " + heldItem;
        } else {return currentArea.getFullDescription() + "\nInventář je prázdný.";}
    }
}
