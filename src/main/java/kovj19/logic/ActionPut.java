package kovj19.logic;

/**
 * Třída implementující příkaz pro pokládání předmětů z inventáře do aktuální lokace.
 *
 * @author Jan Říha, Jan Kovář
 * @version LS-2021
 */
public class ActionPut implements IAction
{
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public ActionPut(Game game)
    {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu tj.&nbsp; slovo <b>polož</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName()
    {
        return "polož";
    }

    /** 
     * Metoda přesune předmět z inventáře hráče do aktuální lokace. Nejprve zkontroluje 
     * počet parametrů. Pokud nebyl zadán žádný parametr nebo právě jeden (název drženého předmětu),
     * pak přesune držený předmět z inventáře do lokace, ve které se hráč nachází. Pokud bylo zadáno
     * dva a více parametrů <i>(tj. hráč chce položit více předmětů současně)</i>, vrátí chybové hlášení.
     *
     * @param parameters parametry příkazu <i>(očekává se pole s jedním prvkem)</i>
     * @return informace pro hráče, které hra vypíše na konzoli
     */
    @Override
    public String execute(String[] parameters)
    {
        if (parameters.length > 1) {
            return "Tomu nerozumím, neumím položit více věcí současně.";  
        }                                                             
        
        Area currentArea = game.getWorld().getCurrentArea();
        Inventory inventory = game.getWorld().getInventory();
        
        if (inventory.getItemList().isEmpty()) {
            return "Inventář je prázdný, nemáš co položit.";
        }
        
        if (parameters.length == 1) {
            String itemName = parameters[0];
            if (itemName.equals(inventory.getHeldItem().getName())) {
                currentArea.addItem(inventory.getHeldItem());
                inventory.removeItem();
                return "Položil(a) jsi '" + itemName + "' z inventáře do lokace '" + currentArea.getName() + "'.";
            } else {
                return "Tento předmět nedržíš.";
            }
        }  else {
            String itemName = inventory.getHeldItem().getName();
            currentArea.addItem(inventory.getHeldItem());
            inventory.removeItem();
            return "Položil(a) jsi '" + itemName + "' z inventáře do lokace '" + currentArea.getName() + "'.";
        }
    }
}
