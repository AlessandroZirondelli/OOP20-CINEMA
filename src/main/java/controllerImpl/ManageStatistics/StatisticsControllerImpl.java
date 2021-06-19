package controllerImpl.ManageStatistics;

import java.time.LocalDate;

import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.border.TitledBorder;

import controller.CinemaController;
import controller.ManageFilms.FilmsController;
import controller.ManageStatistics.StatisticsController;
import controller.booking.BookingController;
import controllerImpl.ManageFilms.FilmsControllerImpl;
import controllerImpl.booking.BookingControllerImpl;
import model.booking.BookingModel;
import modelImpl.booking.BookingModelImpl;
import utilities.Film;
import utilities.Ticket;
import utilitiesImpl.TicketImpl;
import view.ManageStatistics.StatisticsGUI;
import viewImpl.ManageStatistics.StatisticsImplGUI;

/**
 * Implements Account Controller.
 */
public class StatisticsControllerImpl implements StatisticsController { 

    private BookingController controllerBooking;
    private FilmsController controllerFilm;
    private CinemaController controllerCinema;

    private StatisticsGUI statisticsView;

    /**
     * Constructor for the Statistics Controller.
     */
    public StatisticsControllerImpl() {
        this.statisticsView = new StatisticsImplGUI();
        this.statisticsView.setObserver(this);

        this.controllerBooking = new BookingControllerImpl();
        this.controllerFilm = new FilmsControllerImpl();
        System.out.println();
        System.out.println("Film" + controllerFilm.getFilms());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Film> getMostedWatchedFilm() {
        final Optional<Ticket> ticketOptional = this.controllerBooking.getTicket().stream().max((t1, t2) -> {
            return t1.getSetSeat().size() - t2.getSetSeat().size();
        });
        if (ticketOptional.isPresent()) {
            return controllerFilm.getFilms().stream().filter(f -> f.getID() == ticketOptional.get().getId()).findFirst();
        }

        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<LocalTime> getMostAffluenceHours() {

        final Set<Ticket> set = controllerBooking.getTicket();
        final Set<LocalTime> hours = set.stream().map(t -> t.getTime()).distinct().collect(Collectors.toSet());

        Optional<LocalTime> mostAffluentHours = Optional.empty();

        int val = 0;

        for (final var time: hours) {
            final int temp = set.stream().filter(t -> t.getTime().equals(time)).reduce(0, (partialRes, t) -> partialRes + t.getSetSeat().size(), (res1, res2) -> res1 + res2);

            if (val < temp) {
                mostAffluentHours = Optional.of(time);
                val = temp;
            }
        } 
        return mostAffluentHours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<LocalDate> getMostAffluentDays() {

        final Set<Ticket> set = controllerBooking.getTicket();

        final Set<LocalDate> dates = set.stream().map(t -> t.getDate()).distinct().collect(Collectors.toSet());
        Optional<LocalDate> mostAffluentDate = Optional.empty();

        int val = 0;

        for (final var date: dates) {
            final int temp = set.stream().filter(t -> t.getDate().equals(date)).reduce(0, (partialRes, t) -> partialRes + t.getSetSeat().size(), (res1, res2) -> res1 + res2);

            if (val < temp) {
                mostAffluentDate = Optional.of(date);
                val = temp;
            }
        } 
        return mostAffluentDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getRecessed() {
        double tot = 0;
        for (final var ticket : this.controllerBooking.getTicket()) {
           tot = tot + ticket.getSetSeat().size() * ticket.getPrice();
        }

        return tot;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showStatisticsView() {
        statisticsView.update();
        statisticsView.show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showMenu() {
        controllerCinema.showMenu();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCinemaController(CinemaController cinemaController) {
        this.controllerCinema = cinemaController;
    }

}
