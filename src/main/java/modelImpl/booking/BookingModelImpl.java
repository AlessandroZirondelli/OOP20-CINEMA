package modelImpl.booking;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import model.booking.BookingModel;
import utilities.Factory.*;
import utilities.Film;
import utilities.Seat;
import utilities.Ticket;
import utilitiesImpl.Row;
import utilitiesImpl.SeatImpl;
import utilitiesImpl.SeatState;
import utilitiesImpl.TicketImpl;

public class BookingModelImpl implements BookingModel {
    private final Set<Ticket> setTicket;
    private Set<Seat> seatSelected;

    public BookingModelImpl(final Set<Ticket> setTicket) {
        this.seatSelected = new HashSet<>();
        this.setTicket = setTicket;
    }

    @Override
    public Set<Ticket> getSeats() {
        return setTicket;
    }
    @Override
    public Set<Seat> getSeatsFromProgrammedFilm(final ProgrammedFilm programmedFilm) {
        return setTicket.stream()
                .filter(f -> f.getId() == programmedFilm.getIdProgrammation())
                .filter(f -> f.getDate().equals(programmedFilm.getDate()))
                .filter(f -> f.getTime().equals(programmedFilm.getStartTime()))
                .filter(f -> f.getHall().equals(programmedFilm.getHall()))
                .flatMap(f -> f.getSetSeat().stream())
                .collect(Collectors.toSet());
    }
    @Override
    public void buttonSelected(final Seat seat, final ProgrammedFilm programmedFilm) {
        final Set<Seat> set = this.getSeatsFromProgrammedFilm(programmedFilm);
        if (!set.contains(seat)) {
            if (seatSelected.contains(seat)) {
                seatSelected.remove(seat);
            } else {
                seatSelected.add(seat);
            }
        }

    }
    @Override
    public void newBooking() {
        seatSelected = new HashSet<>();
    }
    @Override
    public Set<Seat> getSeatsSelected() {
        return seatSelected;
    }
    @Override
    public void deleteTicket(final Film film) {
        setTicket.removeIf(t -> t.getId() == film.getID());
    }
    @Override
    public void deleteTicket(final ProgrammedFilm programmedFilm) {
        setTicket.removeIf(t -> {
            return t.getId() == programmedFilm.getIdProgrammation()
                    && t.getDate().isEqual(programmedFilm.getDate())
                    && t.getTime().equals(programmedFilm.getStartTime())
                    && t.getHall().equals(programmedFilm.getHall());
        });
    }
    @Override
    public void bookSeat(final ProgrammedFilm programmedFilm) {
       if (!seatSelected.isEmpty()) { 
           Optional<Ticket> ticket = setTicket.stream()
           .filter(t -> t.getId() == programmedFilm.getIdProgrammation())
           .filter(t -> t.getDate().equals(programmedFilm.getDate()))
           .filter(t -> t.getTime().equals(programmedFilm.getStartTime()))
           .filter(t -> t.getHall().equals(programmedFilm.getHall())).findAny();
           if (ticket.isPresent()) {
               ticket.get().getSetSeat().addAll(seatSelected);
           } else {
              final Ticket newTicket = new TicketImpl(programmedFilm.getDate(), programmedFilm.getStartTime(), seatSelected, programmedFilm.getProgrammationPrice(), programmedFilm.getIdProgrammation(), programmedFilm.getHall());
              setTicket.add(newTicket);
           }
       }

    }
}