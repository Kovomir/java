package kovj19.logic;

/**
 * Třída implementující příkaz pro pohyb mezi herními lokacemi.
 *
 * @author Jan Říha
 * @version LS-2021, 2021-05-10
 */
public class ActionMove implements IAction
{
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public ActionMove(Game game)
    {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu tj.&nbsp; slovo <b>jdi</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName()
    {
        return "jdi";
    }

    /**
     * Metoda se pokusí přesunout hráče do jiné lokace. Nejprve zkontroluje počet
     * parametrů. Pokud nebyl zadán žádný parametr <i>(tj. neznáme cíl cesty)</i>,
     * nebo bylo zadáno dva a více parametrů <i>(tj. hráč chce jít na více míst
     * současně)</i>, vrátí chybové hlášení. Pokud byl zadán právě jeden parametr,
     * zkontroluje, zda s aktuální lokací sousedí lokace s daným názvem <i>(tj.
     * zda z aktuální lokace lze jít do požadovaného cíle)</i>. Pokud ne, vrátí
     * chybové hlášení. Dále zkontroluje, zda zadaný východ není zamčený. V takovém
     * případě vypíše chybovou hlášku. Pokud všechny kontroly proběhnou v pořádku, 
     * provede přesun hráče do cílové lokace a vrátí její popis.
     *
     * @param parameters parametry příkazu <i>(očekává se pole s jedním prvkem)</i>
     * @return informace pro hráče, které hra vypíše na konzoli
     */
    @Override
    public String execute(String[] parameters)
    {
        if (parameters.length < 1) {
            return "Tomu nerozumím, musíš mi říct, kam mám jít.";
        }

        if (parameters.length > 1) {
            return "Tomu nerozumím, neumím jít do více lokací současně.";
        }

        String areaName = parameters[0];
        Area currentArea = game.getWorld().getCurrentArea();

        if (!currentArea.hasExit(areaName)) {
            return "Do lokace '" + areaName + "' se odsud jít nedá.";
        }

        Area targetArea = currentArea.getExit(areaName);
        if (targetArea.getLocked() == true) {
            return "Do lokace '" + areaName + "' se jít nedá. Je zamčená.";
        }
        game.getWorld().setCurrentArea(targetArea);

        return targetArea.getFullDescription();
    }
}
