package kovj19.logic;
/**
 * Třída implementuje jednotlivé předměty ve hře, které se mohou nacházet v lokaci,
 * či v inventáři. S předměty lze interagovat příkazy a přístup k některým z nich 
 * na vyvýšených místech je nutné nejprve odemknout pomocí pžíkazu skoč.
 * 
 * @author Jan Říha, Jan Kovář
 * @version LS-2021
 */
public class Item implements Comparable<Item>
{
    private String name;
    private String description;
    private boolean moveable;
    private boolean reachable;
    
    /**
     * Konstruktor třídy, vytvoří předmět se zadaným názvem, popisem a parametry určujícími jeho přenositelnost a dosažitelnost.
     *
     * @param name název předmětu <i>(jednoznačný identifikátor, musí se jednat o text bez mezer)</i>
     * @param description podrobnější popis předmětu
     * @param moveable příznak, zda je hráč schopen přemístit do inventáře
     * @param reachable příznak, zda hráč na předmět dosáhne. Pokud tomu tak není, musí předmět odemknout pomocí příkazu skoč.
     */
    public Item(String name, String description, boolean moveable, boolean reachable)
    {
        this.name = name;
        this.description = description;
        this.moveable = moveable;
        this.reachable = reachable;
    }
    
    /**
     * Zjednodušený konstruktor třídy, vytvoří předmět se zadaným názvem, popisem a parametry určujícími 
     * jeho přenositelnost a dosažitelnost. Je určený pro pohodlnější vytváření předmětů.
     *
     * @param name název předmětu <i>(jednoznačný identifikátor, musí se jednat o text bez mezer)</i>
     * @param description podrobnější popis předmětu
     * @param moveable příznak, zda je hráč schopen přemístit do inventáře
     * @param reachable příznak, zda hráč na předmět dosáhne. Pokud tomu tak není, musí předmět odemknout pomocí příkazu skoč.
     */
    public Item(String name, String description)
    {
        this(name, description, true, true);
    }
    
    /**
     * Metoda vrací název předmětu, který byl zadán při vytváření instance jako
     * parametr konstruktoru. Identifikátor předmětu nemusí být jednoznačný pro nepřenositelné
     * předměty, které se nachází v lokaci pouze jednou. Instance se schodným názvem jsou pak
     * rozlišeny jejich lokací. Aby hra správně fungovala, název lokace nesmí obsahovat mezery,
     * v případě potřeby můžete více slov oddělit pomlčkami, použít camel-case apod.
     *
     * @return název předmětu
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Metoda vrací popis předmětu.
     *
     * @return popis předmětu
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Metoda vrací příznak, zda lze předmět přemístit do inventáře.
     *
     * @return atribut moveable předmětu
     */
    public boolean isMoveable()
    {
        return moveable;
    }
    
    /**
     * Metoda nastaví příznak, zda lze předmět přemístit do inventáře.
     * 
     * @param moveable boolean, který se nastaví do atributu předmětu
     */
    public void setMoveable(boolean moveable)
    {
        this.moveable = moveable;
    }
    
    /**
     * Metoda vrací příznak, zda hráč na předmět dosáhne (zda byl zpřístupněn pomocí příkazu skoč).
     *
     * @return atribut reachable předmětu
     */
    public boolean isReachable()
    {
        return reachable;
    }
    
    /**
     * Metoda nastaví příznak, zda hráč na předmět dosáhne (zda byl zpřístupněn pomocí příkazu skoč).
     * 
     * @param reachable boolean, který se nastaví do atributu předmětu
     */
    public void setReachable(boolean reachable)
    {
        this.reachable = reachable;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (o == this) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (o instanceof Item) {
            Item item = (Item) o;

            return name.equals(item.getName());
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }

    @Override
    public int compareTo(Item item)
    {
        return name.compareTo(item.getName());
    }
}
