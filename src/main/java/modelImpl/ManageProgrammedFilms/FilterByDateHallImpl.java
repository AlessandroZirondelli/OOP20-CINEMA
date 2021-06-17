package modelImpl.ManageProgrammedFilms;

import java.time.LocalDate;
import java.util.function.Predicate;

import model.ManageProgrammingFilms.Filter;
import utilities.Factory.ProgrammedFilm;
import utilitiesImpl.Hall;

/** 
 * Describe a filter by date and hall on programmed film.
 * */
public final class FilterByDateHallImpl implements Filter<ProgrammedFilm> {
    private final LocalDate localDate; // date
    private final Hall hall; // hall

    public FilterByDateHallImpl(final LocalDate localDate, final Hall hall) {
        super();
        this.localDate = localDate;
        this.hall = hall;
    }

    @Override
    public Predicate<ProgrammedFilm> getPredicate() {
        return pf ->  pf.getHall() == hall && pf.getDate().equals(localDate);
    }

}
