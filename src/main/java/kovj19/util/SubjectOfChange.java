package kovj19.util;

public interface SubjectOfChange {
    /**
     * Registrace pozorovatele změn
     * @param observer pozorovatel
     */
    void registerObserver(Observer observer);

    /**
     * Odebrání ze seznamu pozorovatelů
     * @param observer pozorovatel
     */
    void unregisterObserver(Observer observer);

    /**
     * Upozornění registrovaných pozorovatelů na změnu
     */
    void notifyObservers();
}
