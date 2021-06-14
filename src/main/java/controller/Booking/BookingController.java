package controller.Booking;

import java.util.Set;

import controller.CinemaController;
import controller.ManageFilms.FilmsController;
import controller.ManageProgrammingFilms.ProgrammingFilmsController;
import utilities.Film;
import utilities.Ticket;
import utilities.Factory.ProgrammedFilm;

public interface BookingController {
        void start();
        void showMenu();
        Set<Ticket> getTicket();
        void deleteTicket(Film film);
        void deleteTicket(ProgrammedFilm programmedFilm);
        void setCinemaController(CinemaController observer);
}
