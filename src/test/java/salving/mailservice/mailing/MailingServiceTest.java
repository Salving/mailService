package salving.mailservice.mailing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import salving.mailservice.MailApplication;
import salving.mailservice.postoffice.PostOffice;
import salving.mailservice.postoffice.PostOfficeNotFoundException;
import salving.mailservice.postoffice.PostOfficeRepository;
import salving.mailservice.transfer.Transfer;
import salving.mailservice.transfer.TransferRepository;
import salving.mailservice.transfer.TransferType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MailApplication.class)
class MailingServiceTest {
    @Autowired
    MailingService mailingService;

    @MockBean
    PostOfficeRepository postOfficeRepository;

    @MockBean
    MailingRepository mailingRepository;

    @MockBean
    TransferRepository transferRepository;


    private static Mailing validMailing() {
        return new Mailing(1L, MailType.LETTER, "100001", "testAddress", "testDestination");
    }

    private static PostOffice validPostOffice() {
        return new PostOffice("100000", "testOffice", "testOfficeAddress");
    }

    private static List<Transfer> validTransferList() {
        ArrayList<Transfer> transfers = new ArrayList<>();
        Mailing mailing = validMailing();
        transfers.add(new Transfer(100L, mailing, "100000", TransferType.REGISTRATION, new Date()));
        transfers.add(new Transfer(101L, mailing, "200000", TransferType.DEPARTURE, new Date()));
        transfers.add(new Transfer(101L, mailing, "300000", TransferType.ARRIVAL, new Date()));
        transfers.add(new Transfer(101L, mailing, mailing.getDestinationIndex(), TransferType.DELIVERY, new Date()));

        return transfers;
    }

    @Test
    void registerMailingReturnsTransfer() {
        // given
        Mailing mailing = validMailing();
        PostOffice postOffice = validPostOffice();
        Date now = new Date();

        when(postOfficeRepository.findByIndex(postOffice.getIndex())).thenReturn(Optional.of(postOffice));

        when(mailingRepository.save(any())).thenReturn(mailing);

        when(transferRepository.save(any())).thenReturn(
                new Transfer(10L, mailing, postOffice.getIndex(), TransferType.REGISTRATION, now));

        // when
        Transfer transfer = mailingService.registerMailing(mailing, "100000", now);

        // then
        assertEquals(mailing, transfer.getMailing());
        verify(postOfficeRepository).findByIndex(any());
        verify(mailingRepository).save(any());
        verify(transferRepository).save(any());
    }

    @Test
    void registerMailingThrowsExceptionWhenPostOfficeNotExist() {
        // given
        Mailing mailing = validMailing();
        when(postOfficeRepository.findByIndex(any())).thenReturn(Optional.empty());

        // then
        assertThrows(PostOfficeNotFoundException.class,
                () -> mailingService.registerMailing(mailing, "100000", new Date()));
    }

    @Test
    void getStatusReturnsStatus() {
        //given
        Mailing mailing = validMailing();

        when(mailingRepository.findById(anyLong())).thenReturn(Optional.of(mailing));

        when(transferRepository.findAllByMailingOrderByDate(any())).thenReturn(validTransferList());

        // when
        MailingStatus status = mailingService.getStatus(mailing.getId());

        // then
        assertEquals(mailing.getId(), status.getMailingId());
        assertEquals(4, status.getHistory().size());
        assertEquals(TransferType.DELIVERY ,status.getStatus());
    }

    @Test
    void getStatusThrowsExceptionWhenMailingNotExist() {
        // given
        when(mailingRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThrows(MailingNotFoundException.class, () -> mailingService.getStatus(0));
    }

    @Test
    void getMailingThrowsExceptionWhenMailingNotExist() {
        // given
        when(mailingRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThrows(MailingNotFoundException.class, () -> mailingService.getMailing(0));
    }
}