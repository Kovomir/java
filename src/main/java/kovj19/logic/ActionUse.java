package kovj19.logic;

/**
 * Třída implementující příkaz pro použití a interakci s předměty.
 *
 * @author Jan Říha, Jan Kovář
 * @version LS-2021
 */
public class ActionUse implements IAction
{
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public ActionUse(Game game)
    {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu tj.&nbsp; slovo <b>použij</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName()
    {
        return "použij";
    }

    /** 
     * Metoda způsobí, že se kocour pokusí použít předmět nacházející se v aktuální lokaci. Nejprve zkontroluje 
     * počet parametrů. Pokud byl zadán jiný počet parametrů než jeden, pak vypíše chybovou hlášku.
     * Pokud byl zadán název některého z vybraných předmětů, který se nachází v aktuální lokaci, pak kocour provedem
     * danou interakci.
     *
     * @param parameters parametry příkazu <i>(očekává se pole s jedním prvkem)</i>
     * @return informace pro hráče, které hra vypíše na konzoli
     */
    @Override
    public String execute(String[] parameters)
    {
        if (parameters.length < 1) {
            return "Tomu nerozumím, musíš mi říct, co mám použít.";
        }

        if (parameters.length > 1) {
            return "Tomu nerozumím, neumím použít více věcí současně.";  
        }                                                                
                                                                         
        String itemName = parameters[0];
        Area currentArea = game.getWorld().getCurrentArea();
        int diningPeople = game.getWorld().getDiningPeopleCnt();
        
        if (!currentArea.containsItem(itemName)) {
            return "Předmět '" + itemName + "' tady není.";
        }
        
        if (!currentArea.getItem(itemName).isReachable()) {
            return "Na předmět '" + itemName + "' nedosáhneš.";
        }
        
        if (itemName.equals("ovladač")){
            if(game.getWorld().isTvOff()){
                return "Televize je již vypnutá.";
            } else {
                game.getWorld().turnOffTv();
                return "Sednul sis na ovladač a televize se vypnula.";
            }
        }
        
        if (itemName.equals("klec")){
            if(game.getWorld().isCageOpen()){
                return "Klec je již otevřená a zeje prázdnotou.";
            } else {
                game.getWorld().openCage();
                game.getWorld().setDiningPeopleCnt(diningPeople - 1);
                return "Otevřel jsi tlapkou klec. Kubík poskočil a rychle odlétl z pokoje.\n"
                        + "Někdo ho bude muset jít chytit.\n"
                        + "Počet lidí v jídelně je nyní " + game.getWorld().getDiningPeopleCnt() +".";
            }
        }
        
        return "Tohle neumím použít.";
    }
}
