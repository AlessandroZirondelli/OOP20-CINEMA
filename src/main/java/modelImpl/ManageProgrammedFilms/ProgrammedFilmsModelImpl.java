package modelImpl.ManageProgrammedFilms;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import exceptions.ProgrammationNotAvailableException;
import model.ManageProgrammingFilms.ManagerProgrammingFilms;
import model.ManageProgrammingFilms.ProgrammedFilmsModel;
import utilities.Film;
import utilities.TimeSlot;
import utilities.Factory.ProgrammedFilm;
import utilities.Factory.TimeSlotFactory;
import utilitiesImpl.FactoryImpl.TimeSlotFactoryImpl;
/** 
 * Describe a model where all data about programmed films are stored.
 * */
public final class ProgrammedFilmsModelImpl implements ProgrammedFilmsModel {

    private final List<ProgrammedFilm> programmedFilms;
    private final ManagerProgrammingFilms manager;

    public ProgrammedFilmsModelImpl(final List<ProgrammedFilm> programmedFilms) {
        this.programmedFilms = programmedFilms;
        manager = new ManagerProgrammingFilmsImpl(programmedFilms);
    }
    public ProgrammedFilmsModelImpl() {
        this.programmedFilms = new ArrayList<>();
        this.manager = new ManagerProgrammingFilmsImpl(programmedFilms);
    }

    /** 
     * Add film programmation. If it's not available throws ProgrammationNotAvailableException.
     * @param programmedFilm to add.
     * @throws ProgrammationNotAvailableException 
     */
    @Override
    public void addFilmProgrammation(final ProgrammedFilm programmedFilm) throws ProgrammationNotAvailableException {
        final TimeSlotFactory timeSlotFactory = new TimeSlotFactoryImpl();
        final TimeSlot timeSlot = timeSlotFactory.createTimeSlot(programmedFilm.getStartTime(), programmedFilm.getEndTime());
        if (!this.isAvailableProgrammation(timeSlot, programmedFilm.getDate(), programmedFilm.getHall())) {
           throw new ProgrammationNotAvailableException();
        } else {
            programmedFilms.add(programmedFilm); 
        }
    }
    /** 
     * Delete film programmation. 
     * @param programmedFilm to delete.
     */
    @Override
    public void deleteFilmProgrammation(final ProgrammedFilm programmedFilm) {
        programmedFilms.remove(programmedFilm);
    }
    /** 
     * Call manager to verify availability.
     * @param timeSlotToCheck time slot to check
     * @param date date to check
     * @param hall hall to check
     * @return boolean 
     * */
    @Override
    public boolean isAvailableProgrammation(final TimeSlot timeSlotToCheck, final LocalDate date, final int hall) {
        return manager.isAvailableProgrammation(timeSlotToCheck, date, hall);
    }
    /** 
     * Get all programmed films as list.
     * @return programmedFilms 
     * */
    @Override
    public List<ProgrammedFilm> getAllProgrammedFilm() {
        return this.programmedFilms;
    }
    /**Get ManagerProgrammingFilms.
     * @return manager
    */
    @Override
    public ManagerProgrammingFilms getManagerProgrammingFilms() {
        return this.manager;
    }
    /**Delete all programmation of specific film.
     * @param film film to delete.
    */
    @Override
    public void deleteAllFilmProgrammation(final Film film) {
       this.programmedFilms.removeIf(pf -> pf.getIdProgrammation() == film.getID());
    }

}
