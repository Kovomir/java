package kovj19.logic;

/**
 * Třída implementující příkaz pro shození předmětů z vyvýšených míst.
 *
 * @author Jan Říha, Jan Kovář
 * @version LS-2021
 */
public class ActionTumble implements IAction
{
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public ActionTumble(Game game)
    {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu tj.&nbsp; slovo <b>shoď</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName()
    {
        return "shoď";
    }

    /** 
     * Metoda způsobí, že se kocour pokusí shodit předmět v aktuální lokaci. Nejprve zkontroluje 
     * počet parametrů. Pokud byl zadán jiný počet parametrů než jeden, pak vypíše chybovou hlášku.
     * Pokud byl zadán název některého z vybraných předmětů na vyvýšených místech, na který kocour dosáhne,
     * pak ho kocour shodí.
     *
     * @param parameters parametry příkazu <i>(očekává se pole s jedním prvkem)</i>
     * @return informace pro hráče, které hra vypíše na konzoli
     */
    @Override
    public String execute(String[] parameters)
    {
        if (parameters.length < 1) {
            return "Tomu nerozumím, musíš mi říct, co mám shodit.";
        }

        if (parameters.length > 1) {
            return "Tomu nerozumím, neumím shodit více věcí současně.";  
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
        
        if (itemName.equals("talíře") || itemName.equals("váza")){
            currentArea.removeItem(itemName);
            game.getWorld().setDiningPeopleCnt(diningPeople - 1);
            return "Kocour shodil předmět '" + itemName + "'. Jeden z členů rodiny těch musí ten nepořádek uklidit.\n"
                    + "Počet lidí v jídelně je nyní " + game.getWorld().getDiningPeopleCnt() +".";
        }
        
        if (itemName.equals("míč")){
            if(currentArea.getName().equals("chodba")){
                if (currentArea.getItem("pes").isReachable()){
                    currentArea.removeItem("míč");
                    currentArea.removeItem("pes");
                    game.getWorld().setDiningPeopleCnt(diningPeople - 1);
                    return "Kocour shodil míč ze schodů. Rex se za ním ihned pustil a nadělal strašnou neplechu.\n"
                    + "Počet lidí v jídelně je nyní " + game.getWorld().getDiningPeopleCnt() +".";
                } else {
                    return "Zkus nejdříve probudit Rexe, jinak to nebude mít žádný výsledek.";
                }
            } else {
                return "Měl bys zkusit donést míč Rexovi, určitě bude rád.";    
            }
        }
        return "Tento předmět nemohu shodit.";
    }
}
