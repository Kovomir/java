package kovj19.logic;

import java.util.*;

/**
 * Hlavní třída hry. Vytváří a uchovává odkaz na instanci třídy {@link GameWorld}
 * a také vytváří seznam platných příkazů a instance tříd provádějících jednotlivé
 * příkazy.
 * <p>
 * Během hry třída vyhodnocuje jednotlivé příkazy zadané uživatelem.
 *
 * @author Jan Říha
 * @version LS-2021, 2021-05-10
 */
public class Game
{
    private boolean gameOver;
    private GameWorld world;
    private Set<IAction> actions;
    /**
     * Konstruktor třídy, vytvoří hru a množinu platných příkazů. Hra po
     * vytvoření již běží a je připravená zpracovávat herní příkazy.
     */
    public Game()
    {
        gameOver = false;
        world = new GameWorld();

        actions = new HashSet<>();
        actions.add(new ActionHelp());
        actions.add(new ActionTerminate(this));
        actions.add(new ActionMove(this));
        actions.add(new ActionInvestigate(this));
        actions.add(new ActionLookAround(this));
        actions.add(new ActionPick(this));
        actions.add(new ActionPut(this));
        actions.add(new ActionMeow(this)); 
        actions.add(new ActionJump(this));
        actions.add(new ActionUse(this)); 
        actions.add(new ActionTumble(this));
    }

    /**
     * Metoda vrací informaci, zda hra již skončila <i>(je jedno, jestli
     * výhrou, prohrou nebo příkazem 'konec')</i>.
     *
     * @return {@code true}, pokud hra již skončila; jinak {@code false}
     */
    public boolean isGameOver()
    {
        return gameOver;
    }

    /**
     * Metoda nastaví příznak indikující, zda hra skončila. Metodu
     * využívá třída {@link ActionTerminate}, mohou ji ale použít
     * i další implementace rozhraní {@link IAction}.
     *
     * @param gameOver příznak indikující, zda hra již skončila
     */
    public void setGameOver(boolean gameOver)
    {
        this.gameOver = gameOver;
    }

    /**
     * Metoda vrací odkaz na mapu prostorů herního světa.
     *
     * @return mapa prostorů herního světa
     */
    public GameWorld getWorld()
    {
        return world;
    }

    /**
     * Metoda zpracuje herní příkaz <i>(jeden řádek textu zadaný na konzoli)</i>.
     * Řetězec uvedený jako parametr se rozdělí na slova. První slovo je považováno
     * za název příkazu, další slova za jeho parametry.
     * <p>
     * Metoda nejprve ověří, zda první slovo odpovídá hernímu příkazu <i>(např.
     * 'napoveda', 'konec', 'jdi' apod.)</i>. Pokud ano, spustí obsluhu tohoto
     * příkazu a zbývající slova předá jako parametry.
     *
     * @param line text, který hráč zadal na konzoli jako příkaz pro hru
     * @return výsledek zpracování <i>(informace pro hráče, které se vypíšou na konzoli)</i>
     */
    public String processAction(String line)
    {
        String[] words = line.split("[ \t]+");

        String actionName = words[0];
        String[] actionParameters = new String[words.length - 1];

        for (int i = 0; i < actionParameters.length; i++) {
            actionParameters[i] = words[i + 1];
        }

        String result = "Tomu nerozumím, tento příkaz neznám.";

        for (IAction executor : actions) {
            if (executor.getName().equals(actionName)) {
                result = executor.execute(actionParameters);
            }
        }

        if (world.getGameState() != GameState.PLAYING) {
            gameOver = true;
        }

        return result;
    }

    /**
     * Metoda vrací úvodní text pro hráče, který se vypíše na konzoli ihned po
     * zahájení hry.
     *
     * @return úvodní text a popis první lokace
     */
    public String getPrologue()
    {
        return "Vítejte!\n"
                + "V této hře jste hladovým kocourem, který se snaží ukořistit lososa ze stolu obědvajících páníčků.\n"
                + "Nevíte-li, jak pokračovat, zadejte příkaz 'nápověda'.\n"
                + world.getCurrentArea().getFullDescription();
    }

    /**
     * Metoda vrací závěrečný text pro hráče, který se vypíše na konzoli po ukončení
     * hry. Metoda se zavolá pro všechna možná ukončení hry <i>(hráč vyhrál, hráč ukončil hru příkazem 'konec')</i>.
     *
     * @return závěrečný text
     */
    public String getEpilogue()
    {
        String epilogue = "Díky, že sis zahrál(a).";

        GameState gameState = world.getGameState();

        if (gameState == GameState.WON) {
            epilogue = "Vyhrál(a) jsi.\n\n" + epilogue;
        }

        return epilogue;
    }
}
