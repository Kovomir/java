package kovj19.logic;

/**
 * Třída implementující příkaz pro zobrazení nápovědy ke hře.
 *
 * @author Jan Říha, Jan Kovář
 * @version LS-2021
 */
public class ActionHelp implements IAction
{
    /**
     * Metoda vrací název příkazu tj.&nbsp; slovo <b>nápověda</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName()
    {
        return "nápověda";
    }

    /**
     * Metoda vrací obecnou nápovědu ke hře a všechny použitelné příkazy s jejich popisem.
     *
     * @param parameters parametry příkazu <i>(aktuálně se ignorují)</i>
     * @return nápověda, kterou hra vypíše na konzoli
     */
    @Override
    public String execute(String[] parameters)
    {
        return "Tvým úkolem je odlákat všech 5 členů rodiny z jídelny za pomoci interakcí s různými herními předměty a postavami."
                + "\nJakmile je jídelna prázdná, vyskoč na jídelní stůl s lososen a vyhraješ.\n"
                + "Použitelné příkazy jsou:\n"
                + "1. 'nápověda' je příkaz bez parametrů. Vypisuje tuto nápovědu.\n"
                + "2. 'konec' ukončí hru.\n"
                + "3. 'jdi' je příkaz s právě jedním parametrem, který udává název lokace, do které se kocour pokusí přemístit.\n"
                + "Vykoná se však pouze tehdy, pokud je zadaná lokace jedním z východů z místnosti, kde se hráč aktuálně nachází.\n"
                + "4. 'rozhlédni_se' je příkaz bez parametrů, který vypíše podrobné informace o místnosti a o inventáři.\n"
                + "5. 'prozkoumej' je příkaz s jedním parametrem, který je názvem předmětu, o kterém se, pokud se nachází v aktuální místnosti, vypíše jeho popis.\n"
                + "6. 'skoč' je příkaz s jedním parametrem, který udává předmět, na který se kocour pokusí skočit. Lze jej použít\n"
                + "na vybrané předměty v herním světě. Skok zpřístupní a umožní interakci s jinými, nedosažitelnými předměty.\n"
                + "Dále ukončí hru a hráč vítězí, pokud kocour skočí na stůl v prázdné jídelně.\n"
                + "7. 'seber' je příkaz s jedním parametrem, který udává název předmětu, který se odebere z aktuální lokace a přesune se do inventáře.\n"
                + "8. 'polož' je příkaz, který po zadání bez parametru přesune jediný předmět z inventáře do aktuální lokace.\n"
                + "To samé udělá, pokud je v jednom parametru zadán název drženého předmětu.\n"
                + "9. 'zamňoukej' je příkaz s jedním parametrem, který udává název předmětu z aktuální lokace, ke kterému si kocour stoupne a začne mňoukat.\n"
                + "To lze využít k upoutání pozornosti členů rodiny.\n"
                + "10. 'shoď'je příkaz s jedním parametrem, který udává název předmětu z aktuální lokace, který kocour shodí. Lze jej použít jen na pár vybraných předmětů.\n"
                + "11. 'použij'je příkaz s jedním parametrem, který udává název předmětu z aktuální lokace, s kterým se kocour pokusí interagovat. Lze jej použít jen na pár vybraných předmětů.\n";
    }
}
