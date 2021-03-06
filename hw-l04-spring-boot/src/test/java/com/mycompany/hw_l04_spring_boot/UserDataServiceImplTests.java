package com.mycompany.hw_l04_spring_boot;

import com.mycompany.hw_l04_spring_boot.domain.User;
import com.mycompany.hw_l04_spring_boot.service.IOService;
import com.mycompany.hw_l04_spring_boot.service.MessageSourceService;
import com.mycompany.hw_l04_spring_boot.service.UserDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserDataServiceImplTests {

    @MockBean
    private IOService ioService;

    @MockBean
    private MessageSourceService msService;

    @Autowired
    private UserDataService userDataService;

    @Test
    void getUserTest() {
        when(msService.getMessage("user.name")).thenReturn("");
        when(msService.getMessage("user.surname")).thenReturn("");
        doNothing().when(ioService).out("");
        doNothing().when(ioService).out("\n");

        var user = new User("John", "Smith");
        when(ioService.readString())
                .thenReturn("John")
                .thenReturn("Smith");

        assertEquals(user, userDataService.getUser());
    }
}
