package kovj19.logic;

/**
 * Třída implementující příkaz pro skákání na vyvýšená místa. Příkaz zpřístupňuje další předměty.
 * Skok na stůl v prázdné jídelně ukončí hru a hráč vyhraje.
 *
 * @author Jan Říha, Jan Kovář
 * @version LS-2021
 */
public class ActionJump implements IAction
{
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public ActionJump(Game game)
    {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu tj.&nbsp; slovo <b>skoč</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName()
    {
        return "skoč";
    }

    /** 
     * Metoda umožňuje kocourovi vyskočit na určité vyvýšené věci v herním světě,
     * což umožní přístup k dalším předmětům. Takto získaný přístup k předmětům 
     * je permanentní, hráči proto stačí na každý předmět vyskočit pouze jednou
     * za celý průchod hrou. Kocour si dále již pamatuje, kde se předměty nachází.
     * Dále je metoda použita pro dosažení cíle hry.
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
            return "Tomu nerozumím, musíš mi říct, na co mám vyskočit.";
        }

        if (parameters.length > 1) {
            return "Tomu nerozumím, neumím vyskočit na více věcí současně.";  
        }                                                                
                                                                         
        String itemName = parameters[0];
        Area currentArea = game.getWorld().getCurrentArea();
        int diningPeople = game.getWorld().getDiningPeopleCnt();
        
        if (!currentArea.containsItem(itemName)) {
            return "Předmět '" + itemName + "' tady není.";
        }
        
        if (itemName.equals("pes")){
            currentArea.getItem("pes").setReachable(true);
            return "Skočil jsi na Rexe, který se s trhnutím probudil a shodil tě ze zad.\n"
                    + "Nevypadá zrovna nadšeně. Třeba bys ho mohl něčím rozveselit?";
        }
        
        if (itemName.equals("stůl") && currentArea.getName().equals("ložnice")){
            currentArea.getItem("váza").setReachable(true);
            return "Vyskočil jsi na stůl, na kterém jsi našel vázu s květinami.";
        }
        
        if (itemName.equals("pult")){
            currentArea.getItem("talíře").setReachable(true);
            return "Vyskočil jsi kuchyňskou linku, kde jsi našel hromadu talířů.";
        }
        
        if (itemName.equals("stůl") && currentArea.getName().equals("jídelna")){
            if(diningPeople != 0){
                return "Nemůžeš se jen tak procházet po jídelním stole, dokud jsou v jídelně lidi.";
            } else {
                currentArea.getItem("losos").setReachable(true);
                return "Vyskočil jsi na jídelní stůl a beze svědků ses výborně najedl.\n"
                        + "Pevně věříš, že se páníčci nikdy nedovtípí pravdy o tom, kdo jim snědl oběd.";
            }
        }
        
        if (currentArea.getName().equals("jídelna") && itemName.equals("myš")){
            game.getWorld().setDiningPeopleCnt(diningPeople - 1);
            return "Jakmile mrtvou myš uviděla nejmladší dcera, s jekotem utekla od večeře.\n"
                    + "Počet lidí v jídelně je nyní " + game.getWorld().getDiningPeopleCnt() +".";
        }
        
        return "Na to nemůžeš vyskočit.";
    }
}
