package modelImpl.ManageProgrammedFilms;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import model.ManageProgrammingFilms.HandlerList;
import model.ManageProgrammingFilms.ManagerProgrammingFilms;
import utilities.TimeSlot;
import utilities.Factory.ProgrammedFilm;
import utilitiesImpl.Hall;
import utilitiesImpl.FactoryImpl.TimeSlotImpl;
/** 
 * Manager programming films. It has a reference of list to manage and he has a specific handler list.
 * */
public final class ManagerProgrammingFilmsImpl implements ManagerProgrammingFilms {
     private final  List<ProgrammedFilm> listToManage;
     private final HandlerList<ProgrammedFilm> handlerList;

     public ManagerProgrammingFilmsImpl(final List<ProgrammedFilm> listToManage) {
            this.listToManage = listToManage;
            this.handlerList = new HandlerListImpl<>();
     }
     /** 
      * Check if a specific timeslot, date and hall are available.
      * @param timeSlotToCheck time slot to check
      * @param date date to check
      * @param hall hall to check
      * @return boolean 
      * */
     @Override
     public boolean isAvailableProgrammation(final TimeSlot timeSlotToCheck, final LocalDate date, final Hall hall) { // check if timeslot is available for specific date and hall 
            //final TimeSlotFactory timeSlotFactory = new TimeSlotFactoryImpl();
            return !this.handlerList.filterBy(listToManage, new FilterByDateHallImpl(date, hall))
                    .stream()
                    .anyMatch(pf -> {
                       if (this.isAvailableTimeSlot(new TimeSlotImpl(pf.getStartTime(), pf.getEndTime()), timeSlotToCheck)) { // if it's available it means that there isn't any match , so return false
                           return false;
                       } 
                       return true;
                    }
            ); 
        }
     private  boolean isAvailableTimeSlot(final TimeSlot inserted, final TimeSlot toCheck) { // check if specifif time slot is available compare to another time slot
            final LocalTime insertedStartTime = inserted.getStartTime();
            final LocalTime insertedEndTime = inserted.getEndTime();
            final LocalTime toCheckStartTime = toCheck.getStartTime();
            final LocalTime toCheckEndTime = toCheck.getEndTime();

            if (// if toCheckStartTime is between  timeslot
                  (((toCheckStartTime.isAfter(insertedStartTime)) || toCheckStartTime.equals(insertedStartTime)) 
                  && ((toCheckStartTime.isBefore(insertedEndTime)) || toCheckStartTime.equals(insertedEndTime))
                  ) 
                  || //if toCheckStartTime is between  timeslot
                  (((toCheckEndTime.isAfter(insertedStartTime)) || toCheckEndTime.equals(insertedStartTime)) 
                  && ((toCheckEndTime.isBefore(insertedEndTime)) || toCheckEndTime.equals(insertedEndTime))
                  ) 
              ) {
                return false;
              }

            if (toCheckStartTime.isBefore(insertedStartTime) && !toCheckEndTime.isBefore(insertedStartTime)) {
                return false;
            }
            if (toCheckStartTime.isAfter(insertedStartTime) && !toCheckStartTime.isAfter(insertedEndTime)) {
                return false;
            }
            return true;
     }

     /** 
      * Get used handler list.
      * @return HandlerList<ProgrammedFilm> 
      * */
    @Override
    public HandlerList<ProgrammedFilm> getHandlerList() {
        return this.handlerList;
    }


 
 
}
