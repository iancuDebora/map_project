package utils.observer;

/**
 * Interfata pentru Observable.
 */
public interface Observable{

    /**
     * Metoda care adauga un observer.
     * @param e - obiect Observer
     */
    void addObserver(Observer e);

    /**
     * Metoda care elimina un observator.
     */
    void removeObserver();

    /**
     * Metoda care notifica observatorii de schimbari.
     */
    void notifyObservers();
}
