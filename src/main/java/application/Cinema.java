package application;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;

import controller.FilmsController;
import controllerImpl.FilmsControllerImpl;
import controllerImpl.InputOutput.RWcollection;
import controllerImpl.InputOutput.RWfile;
import controllerImpl.InputOutput.RWobject;
import model.ContainerFilmsModel;
import model.ManagerIdsFilms;
import modelImpl.IdsGeneratorImpl;
import modelImpl.ManagerIdsFilmImpl;
import utilities.Film;
import utilities.FilmBasicImpl;
import utilities.FilmFactory;
import utilities.FilmFactoryImpl;
import utilities.GeneralSettings;
import modelImpl.ContainerFilmsModelImpl;

public class Cinema {

    public static void main(final String[] args) {

        final Set<Film> set = new HashSet<>();
        final ManagerIdsFilms managerIdsFilm = new ManagerIdsFilmImpl(new IdsGeneratorImpl());
        //ContainerFilmsModel model = new ContainerFilmsModelImpl();

        final FilmFactory filmFactory = new FilmFactoryImpl(new ManagerIdsFilmImpl(new IdsGeneratorImpl()));
        final Film f1 = filmFactory.createBasicFilm("Spiderman", "Action", "Nice film", Optional.ofNullable("/home/ziro/.application/images/selected/T8HdP0wHZgK74Yq.JPG"),120);
        final Film f2 = filmFactory.createBasicFilm("Batman", "Action", "Nice film", Optional.ofNullable(null),140);
        final Film f3 = filmFactory.createBasicFilm("Thor", "Action", "Good film", Optional.ofNullable(null),120);
        
        Film f5 = filmFactory.createBasicFilmById("ccc", "aaa", "aaa", Optional.ofNullable(null), 12, 7);
        Film f6 = filmFactory.createBasicFilmById("ccc", "aaa", "aaa", Optional.ofNullable(null), 13, 8);
        
        System.out.println(f5.equals(f6));
        
        
        set.add(f1);
        set.add(f2);
        set.add(f3);

        managerIdsFilm.getNewFilmID();
        managerIdsFilm.getNewFilmID();
        managerIdsFilm.getNewFilmID();
        System.out.println(managerIdsFilm.getUsedIDs());
        final FilmsController filmsController = new FilmsControllerImpl(set, managerIdsFilm);
      //  System.out.println(filmsController.getFilms());
        filmsController.showContainerFilmsView();

        //System.out.println(exist);

    }


}
