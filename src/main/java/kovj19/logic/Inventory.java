package kovj19.logic;
import kovj19.util.Observer;
import kovj19.util.SubjectOfChange;

import java.util.*;

/**
 * Třída implementující inventář o velikosti 1.
 *
 * @author Jan Kovář
 * @version LS-2021
 */
public class Inventory implements SubjectOfChange
{
    private ArrayList<Item> itemList;
    private Item heldItem;
    private final Set<Observer> observers = new HashSet<>();
    
    /**
     * Konstruktor třídy, vytvoří nový prázdný inventář.
     */
    public Inventory() {
        itemList = new ArrayList<>();
    }
    
    /**
     * Metoda vrací seznam věcí v inventáři.
     * 
     * @return seznam věcí v inventáři
     */
    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public Set<String> getItemNames() {
        Set<String> itemNamesList = new HashSet<>();
        for (Item item : itemList) {
            itemNamesList.add(item.getName());
            }
        return itemNamesList;
        }
    
    /**
     * Metoda vrací odkaz na držený předmět. Pokud je inventář prázdný, vrací null.
     * 
     * @return držený předmět
     */
    public Item getHeldItem() {
        if(!itemList.isEmpty()) {
            heldItem = itemList.get(0);
        return heldItem;
        }
        else return null;
    }

    public void addItem(Item item) {
        itemList.add(item);
        notifyObservers();
    }

    public void removeItem() {
        itemList.clear();
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer : observers) {
            observer.update();
        }
    }
}
