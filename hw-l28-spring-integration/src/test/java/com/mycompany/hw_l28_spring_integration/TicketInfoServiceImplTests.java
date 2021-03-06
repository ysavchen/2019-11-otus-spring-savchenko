package com.mycompany.hw_l28_spring_integration;

import com.mycompany.hw_l28_spring_integration.domain.Flight;
import com.mycompany.hw_l28_spring_integration.domain.Ticket;
import com.mycompany.hw_l28_spring_integration.exception.NoTicketsLeftException;
import com.mycompany.hw_l28_spring_integration.repositories.TicketRepository;
import com.mycompany.hw_l28_spring_integration.service.TicketInfoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(TicketInfoServiceImpl.class)
public class TicketInfoServiceImplTests {

    @Autowired
    private TicketInfoServiceImpl infoService;

    @MockBean
    private TicketRepository ticketRepository;

    private final Ticket ticket = new Ticket("John Doe", new Flight());

    @Test
    void ticketAvailablePositive() {
        when(ticketRepository.findTicketsByFlightId(anyLong())).thenReturn(List.of(new Ticket()));

        assertEquals(ticket, infoService.ticketAvailable(ticket));
    }

    @Test
    void ticketAvailableNegative() {
        when(ticketRepository.findTicketsByFlightId(anyLong())).thenReturn(
                List.of(new Ticket(), new Ticket(), new Ticket(), new Ticket())
        );

        assertThrows(NoTicketsLeftException.class, () -> infoService.ticketAvailable(ticket));
    }
}
