package kovj19.logic;

import kovj19.util.Observer;
import kovj19.util.SubjectOfChange;

import java.util.HashSet;
import java.util.Set;

/**
 * Třída představující mapu lokací herního světa. V datovém atributu
 * {@link #currentArea} uchovává odkaz na aktuální lokaci, ve které
 * se hráč právě nachází. Z aktuální lokace je možné se prostřednictvím
 * jejích sousedů dostat ke všem přístupným lokacím ve hře.
 * <p>
 * Veškeré informace o stavu hry <i>(mapa prostorů, inventář, vlastnosti
 * hlavní postavy, informace o plnění úkolů apod.)</i> by měly být uložené
 * zde v podobě datových atributů.
 *
 * @author Jan Říha, Jan Kovář
 * @version LS-2021
 */
public class GameWorld implements SubjectOfChange
{
    private Area currentArea;
    private Inventory inventory;
    private int diningPeopleCnt = 5;
    //GardenInstance se používá při odemykání lokace, umožňuje přístup z jiné třídy.
    private Area gardenInstance;
    private boolean cageOpen = false;
    private boolean TvOff = false;

    private final Set<Observer> observers = new HashSet<>();
    /**
     * Konstruktor třídy, vytvoří jednotlivé lokace a propojí je pomocí východů.
     * Dále vytvoří předměty a přidá je do náležitých lokací, vytvoří inventář
     * a nastaví výchozí lokaci hřáče.
     */
    public GameWorld()
    {
        Area diningRoom = new Area("jídelna", "Zde rodina právě obědvá lososa, po kterém tolik toužíš.", 400.0, 100.0);
        Area kitchen = new Area("kuchyň", "V kuchyni je po přípravě oběda poněkud nepořádek. Na kuchyňské lince je nádobí.", 600.0, 100.0);
        Area livingRoom = new Area("obývák", "V obýváku tráví rodinní příslušníci spoustu času sledováním televize.", 200.0, 100.0);
        Area garden = new Area("zahrada", "Na zahradě trávíš spoustu času sledováním ptáků a chytáním myší.", 300.0, 200.0);
        Area corridor = new Area("chodba", "Toto je chodba v patře domu. U schodů tvrdě spí tvůj podstatně mohutnější psí kámoš Rex.", 300.0, 0.0);
        Area bedroom = new Area("ložnice", "Toto je ložnice rodičů. Ze stolku je prima výhled z okna.", 0.0, 0.0);
        Area roomOfChildren = new Area("pokoj", "V dětském pokoji je klec s papouškem Kubíkem, kterého tak rád pozoruješ.", 400.0, 0.0);
        
        inventory = new Inventory();
        Item deadMouse = new Item("myš", "Je to mrtvá myš, kterou jsi schoval z večerního lovu do křoví. Páníčci jistě ocení, když se s nimi o kořist podělíš.");
        Item tree = new Item("strom", "Rád pozoruješ ptáky v korunách tohoto stromu.", false, true);
        Item remote = new Item("ovladač", "Je to ovladač na televizi. Často se o něj vedou rodiné spory. Myslíš si, že víš, jak se používá.");
        Item door = new Item("dveře", "Jsou to dveře na zahradu. Již máš páníčky dobře vycvičené, kdykoliv chceš ven, stačí zamňoukat.", false, true);
        Item television = new Item("televize", "Zábavná věc s pohyblivými obrázky. Páníčci na ní v jednom kuse s údivem zírají.", false, true);
        Item counter = new Item("pult", "Je to kuchyňská linka. Třeba na ní najdeš nějaké zbytky z oběda?.", false, true);
        Item plates = new Item("talíře", "Jsou to talíře na kuchyňské lince. Naskládané na sobě působí docela vratce.", false, false);
        Item ball = new Item("míč", "Tohle je Rexova nejoblíbenější hračka. Stačí míč rozhýbat a nastane psí pohroma.");
        Item cage = new Item("klec", "Je to klec s papouškem Kubíkem. Otevřít ji je pro tebe hračka, ale nemine tě hněv páníčků.", false, true);
        Item dog = new Item("pes", "Je to tvůj spící psí kámoš Rex. Spí dost tvrdě, ale váha padajícího lenivého kocoura ho dokáže spolehlivě probrat.", false, false);
        Item table = new Item("stůl", "Toto je malinký stolek na kterém je váza s květinami.", false, true);
        Item vase = new Item("váza", "Je to váza s květinami na stolku. Matka jí má obzvláště v oblibě.", false, false);
        Item diningTable = new Item("stůl", "Na tomhle stole je vše, co sis kdy přál. Stačí jen odlákat páníčky a skočit vstříc svému štěstí!", false, true);
        Item salmon = new Item("losos", "Tohle je vše, po čem toužíš.", false, false);
        
        diningRoom.addExit(kitchen);
        diningRoom.addExit(livingRoom);
        diningRoom.addItem(diningTable);
        diningRoom.addItem(salmon);
        
        kitchen.addExit(diningRoom);
        kitchen.addItem(counter);
        kitchen.addItem(plates);
        
        livingRoom.addExit(diningRoom);
        livingRoom.addExit(garden);
        livingRoom.addExit(corridor);
        livingRoom.addItem(remote);
        livingRoom.addItem(door);
        livingRoom.addItem(television);
        
        garden.addExit(livingRoom);
        garden.setLocked(true);
        garden.addItem(deadMouse);
        garden.addItem(tree);

        corridor.addExit(roomOfChildren);
        corridor.addExit(bedroom);
        corridor.addExit(livingRoom);
        corridor.addItem(dog);
        
        bedroom.addExit(corridor);
        bedroom.addItem(table);
        bedroom.addItem(vase);
        
        roomOfChildren.addExit(corridor);
        roomOfChildren.addItem(ball);
        roomOfChildren.addItem(cage);

        currentArea = diningRoom;
        
        gardenInstance = garden;
    }

    /**
     * Metoda vrací odkaz na aktuální lokaci, ve které se hráč právě nachází.
     *
     * @return aktuální lokace
     */
    public Area getCurrentArea()
    {
        return currentArea;
    }
    
    /**
     * Metoda nastaví aktuální lokaci. Používá ji příkaz {@link ActionMove}
     * při přechodu mezi lokacemi.
     *
     * @param currentArea lokace, která bude nastavena jako aktuální
     */
    public void setCurrentArea(Area currentArea){
        this.currentArea = currentArea;
        notifyObservers();
    }
    
    /**
     * Metoda vrací odkaz na inventář hráče.
     *
     * @return inventář
     */
    public Inventory getInventory()
    {
        return inventory;
    }
    
    /**
     * Metoda vrací počet lidí v jídelně.
     *
     * @return diningPeopleCnt
     */
    public int getDiningPeopleCnt(){
        return diningPeopleCnt;
    }
    
    /**
     * Metoda nastaví počet lidí v jídelně.
     *
     * @return nový počet lidí v jídelně
     */
    public int setDiningPeopleCnt(int diningPeopleCnt){
        return this.diningPeopleCnt = diningPeopleCnt;
    }
    
    /**
     * Metoda vrací lokaci zahrada, což je použito k přístupu z jiné třídy.
     *
     * @return gardenInstance odkaz na lokaci zahrada
     */
    public Area getGardenInstance(){
        return gardenInstance;
    }
    
    /**
     * Metoda vrací atribut, zda je vyplá televize.
     *
     * @return TvOff boolean, zda je TV vyplá
     */
    public boolean isTvOff(){
        return TvOff;
    }
    
    /**
     * Metoda vvypne televizi.
     */
    public void turnOffTv(){
        TvOff = true;
    }
    
    /**
     * Metoda vrací atribut, zda je klec otevřená.
     *
     * @return TvOff boolean, zda je klec oetvřená
     */
    public boolean isCageOpen(){
        return cageOpen;
    }
    
    /**
     * Metoda otevře klec.
     */
    public void openCage(){
        cageOpen = true;
    }

    /**
     * Metoda vrací aktuální stav hry <i>(běžící hra, výhra)</i>.
     *
     * @return aktuální stav hry
     */
    public GameState getGameState(){
        if (currentArea.getName().equals("jídelna") && currentArea.containsItem("losos")){
            if(currentArea.getItem("losos").isReachable()){
                return GameState.WON;
            }
        }

        return GameState.PLAYING;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
