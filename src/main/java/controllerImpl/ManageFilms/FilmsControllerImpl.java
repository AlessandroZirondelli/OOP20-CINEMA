package controllerImpl.ManageFilms;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.swing.SwingUtilities;

import com.google.gson.reflect.TypeToken;

import controller.ManageFilms.FilmsController;
import controllerImpl.InputOutput.RWobject;
import controllerImpl.InputOutput.RWobjectImpl;
import model.ManageFilms.ContainerFilmsModel;
import model.ManageFilms.ManagerIdsFilms;
import modelImpl.ManageFilms.ContainerFilmsModelImpl;
import modelImpl.ManageFilms.IdsGeneratorImpl;
import modelImpl.ManageFilms.ManagerIdsFilmImpl;
import utilities.Film;
import utilities.ManagerWorkingDIR;
import utilitiesImpl.GeneralSettings;
import utilitiesImpl.ManagerWorkingDIRimpl;
import utilitiesImpl.FactoryImpl.FilmBasicImpl;
import view.ManageFilms.ContainerFilmsGUI;
import view.ManageFilms.InfoFilmsGUI;
import viewImpl.ManageFilms.ContainerFilmsGUIimpl;
import viewImpl.ManageFilms.InfoFilmsGUIimpl;

public class FilmsControllerImpl implements FilmsController {

    private final ContainerFilmsModel model;
    private ContainerFilmsGUI viewFilms;
    private final InfoFilmsGUI infoFilms;
    private final ManagerWorkingDIR managerWorkingDIR;
    

    public FilmsControllerImpl() { // must be invoked on the first use of application 
        
        Optional<Set<Film>> films = this.readFilmsFromFile();
        Optional<ManagerIdsFilms> managerIdsFilm = this.readManagerIdsFilmsFromFile();
        
        if(films.isEmpty()|| managerIdsFilm.isEmpty() ) {
            model = new ContainerFilmsModelImpl(); 
            
        }else {
            model =  new ContainerFilmsModelImpl(films.get(),managerIdsFilm.get()) ;
        }

        viewFilms = new ContainerFilmsGUIimpl(new HashSet<>()); // Empty set, there aren't films
        infoFilms = new InfoFilmsGUIimpl();
        managerWorkingDIR = new ManagerWorkingDIRimpl();

        this.viewFilms.setObserver(this);
        this.infoFilms.setObserver(this);
    }  
    
 
    @Override
    public void addFilm(final Film f) {
        this.model.addFilm(f);
        this.writeFilmsOnFile();
        this.writeManagerIdsFilmsOnFile();
        //System.out.println("Add new film:"+ f);
    }

    @Override
    public void deleteFilm(final Film f) {
        this.model.removeFilm(f);
        this.writeFilmsOnFile();
        this.writeManagerIdsFilmsOnFile();
        //System.out.println("Delete film:"+ f);
    }

    @Override
    public Set<Film> getFilms() {
        return this.model.getFilms();
    }

    @Override
    public void showMenu() { //TODO 
        
    }

    @Override
    public void showContainerFilmsView() {
        viewFilms.update();
        viewFilms.start();
    }

    @Override
    public void showInfoFilmView(final Film f) {
        infoFilms.start();
        infoFilms.loadFilm(f);
        System.out.println("Load specific film"+ f);
    }

    @Override
    public void showNewFilmView() {
        infoFilms.reset();
        infoFilms.start();
    }

    @Override
    public void loadFilm(final Set<Film> loadedFilms) {

    }

    @Override
    public ManagerWorkingDIR getManagerWorkingDIR() {
        return this.managerWorkingDIR;
    }

    @Override
    public ManagerIdsFilms getManagerIdsFilms() {
        return model.getManagerIdsFilms();
    }
    
    private void writeFilmsOnFile() {
         final RWobject<Set<Film>> rw = new RWobjectImpl<>(GeneralSettings.FILMSPATH) ;
         final var type = new TypeToken<Set<Film>>() {}.getType();
         rw.writeObj(model.getFilms(), type);
    }
    
    private void writeManagerIdsFilmsOnFile() {
        final RWobject<ManagerIdsFilms> rw = new RWobjectImpl<>(GeneralSettings.MANAGERIDSFILMSPATH) ;
        final var type = new TypeToken<ManagerIdsFilms>() {}.getType();
        rw.writeObj(model.getManagerIdsFilms(), type);
    }
    
    private Optional<Set<Film>> readFilmsFromFile() {
        final RWobject<Set<Film>> rw = new RWobjectImpl<>(GeneralSettings.FILMSPATH) ;
        final var type = new TypeToken<Set<Film>>() {}.getType();
        return rw.readObj(type);
        
   }
    

    private Optional<ManagerIdsFilms> readManagerIdsFilmsFromFile() {
        final RWobject<ManagerIdsFilms> rw = new RWobjectImpl<>(GeneralSettings.MANAGERIDSFILMSPATH) ;
        final var type = new TypeToken<ManagerIdsFilms>() {}.getType();
        return rw.readObj(type);
    
    }
}
