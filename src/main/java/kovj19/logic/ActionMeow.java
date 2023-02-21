package kovj19.logic;

/**
 * Třída implementující příkaz pro povel pro kocoura, aby zamňoukal u předmětu.
 *
 * @author Jan Říha, Jan Kovář
 * @version LS-2021
 */
public class ActionMeow implements IAction
{
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public ActionMeow(Game game)
    {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu tj.&nbsp; slovo <b>zamňoukej</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName()
    {
        return "zamňoukej";
    }

    /** 
     * Zavolání metody způsobí, že se kocour postaví k věci v místnosti, ve které 
     * se právě nachází a začne hlasitě mňoukat, čímž upoutá pozornost páníčků.
     * Pokud je metodě předán jiný počet parametrů třídy Item než jeden, nebo pokud
     * se daný předmět v místnosti nenachází, pak vypíše chybovou hlášku.
     * 
     * @param parameters parametry příkazu <i>(očekává se pole s jedním prvkem)</i>
     * @return informace pro hráče, které hra vypíše na konzoli
     */
    @Override
    public String execute(String[] parameters)
    {
        if (parameters.length < 1) {
            return "Tomu nerozumím, musíš mi říct, u čeho mám zamňoukat.";
        }

        if (parameters.length > 1) {
            return "Tomu nerozumím, neumím zamňoukat u více věcí současně.";  
        }                                                                
                                                                         
        String itemName = parameters[0];
        Area currentArea = game.getWorld().getCurrentArea();
        int diningPeople = game.getWorld().getDiningPeopleCnt();
        
        if (!currentArea.containsItem(itemName)) {
            return "Předmět '" + itemName + "' tady není.";
        }
        
        if (currentArea.getName().equals("obývák") && itemName.equals("dveře")){
            if(!game.getWorld().isTvOff()){
                return "Kocourovo mňoukání není slyšet přes hluk televize.";
            } else {
                game.getWorld().getGardenInstance().setLocked(false);
                return "Kocour se postavil ke dveřím a začal hlasitě mňoukat.\n"
                        + "Přišel otec a dveře na zahradu beze slova otevřel.";
            }
        }
        
        if (currentArea.getName().equals("jídelna") && itemName.equals("myš")){
            game.getWorld().setDiningPeopleCnt(diningPeople - 1);
            currentArea.removeItem("myš");
            return "Jakmile mrtvou myš uviděla nejmladší dcera Denča, s jekotem utekla od večeře.\n"
                    + "Otec se myši rychle zbavil, ale Denča už odmítá dál večeřet.\n"
                    + "Počet lidí v jídelně je nyní " + game.getWorld().getDiningPeopleCnt() +".";
        }
        
        return "Kocour se postavil k předmětu '" + itemName + "' a začal hlasitě mňoukat.";
    }
}
