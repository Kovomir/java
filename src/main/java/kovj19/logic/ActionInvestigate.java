package kovj19.logic;

/**
 * Třída implementující příkaz pro průzkum konkrétního předmětu.
 *
 * @author Jan Říha, Jan Kovář
 * @version LS-2021
 */
public class ActionInvestigate implements IAction
{
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public ActionInvestigate(Game game)
    {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu tj.&nbsp; slovo <b>prozkoumej</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName()
    {
        return "prozkoumej";
    }

    /**
     * Metoda vrátí detailní popis vybraného předmětu v aktuální lokaci.
     *
     * @param parameters parametry příkazu <i>(očekává se pole s jedním prvkem)</i>
     * @return informace pro hráče, které hra vypíše na konzoli
     */
    @Override
    public String execute(String[] parameters)
    {
        if (parameters.length < 1) {
            return "Tomu nerozumím, musíš mi říct, co mám prozkoumat.";
        }

        if (parameters.length > 1) {
            return "Tomu nerozumím, neumím zkoumat více věcí současně.";
        }

        String itemName = parameters[0];
        Area currentArea = game.getWorld().getCurrentArea();

        //# TODO: Kromě předmětů v aktuální lokaci by bylo vhodné umět prozkoumat
        //# i předměty v inventáři (námět na možné rozšíření)

        if (!currentArea.containsItem(itemName)) {
            return "Předmět '" + itemName + "' tady není.";
        }
        
        Item item = currentArea.getItem(itemName);
        
        if(itemName.equals("pes")){
            if(currentArea.getItem("pes").isReachable()){
                return "Díváš se na předmět '" + itemName + "'.\n"
                        + "Rex je již vzhůru, ale nevypadá zrovna nadšeně. Jistě by ho rozveselil jeho míč.";
            } else {
                return "Díváš se na předmět '" + itemName + "'.\n" +
                        item.getDescription();
            }
        }

        return "Díváš se na předmět '" + itemName + "'.\n" +
                item.getDescription();
    }
}
