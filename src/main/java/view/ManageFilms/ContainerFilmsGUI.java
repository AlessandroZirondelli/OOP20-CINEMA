package view.ManageFilms;

import controller.ManageFilms.FilmsController;

public interface ContainerFilmsGUI {
    /**
        Show a view with all films added in container.
     */
    void start();
    /**
        Set GUI films controller observer.
        @param observer observer to set.
     */
    void setObserver(FilmsController observer);
    /**
        Update and refresh GUI.
     */
    void update();
}
