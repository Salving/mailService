package salving.mailservice.transfer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import salving.mailservice.MailApplication;
import salving.mailservice.mailing.*;
import salving.mailservice.postoffice.PostOffice;
import salving.mailservice.postoffice.PostOfficeNotFoundException;
import salving.mailservice.postoffice.PostOfficeRepository;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MailApplication.class)
class TransferServiceTest {

    @Autowired
    TransferService transferService;

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

    @Test
    void recordArrivalReturnsTransfer() {
        // given
        Mailing mailing = validMailing();
        PostOffice postOffice = validPostOffice();

        when(postOfficeRepository.findByIndex(any())).thenReturn(Optional.of(postOffice));

        when(mailingRepository.findById(anyLong())).thenReturn(Optional.of(mailing));

        when(transferRepository.save(any())).then(invocation -> invocation.getArgument(0));

        // when
        Transfer transfer = transferService.recordArrival(mailing.getId(), postOffice.getIndex(), new Date());

        // then
        assertEquals(TransferType.ARRIVAL, transfer.getType());
        verify(postOfficeRepository).findByIndex(any());
        verify(mailingRepository).findById(anyLong());
        verify(transferRepository).save(any());
    }

    @Test
    void recordArrivalThrowsExceptionWhenPostOfficeNotExist() {
        // given
        when(postOfficeRepository.findByIndex(any())).thenReturn(Optional.empty());

        // then
        assertThrows(PostOfficeNotFoundException.class, () -> transferService.recordArrival(0, "100000", new Date()));
    }

    @Test
    void recordArrivalThrowsExceptionWhenMailingNotExist() {
        // given
        when(postOfficeRepository.findByIndex(any())).thenReturn(Optional.of(validPostOffice()));

        when(mailingRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThrows(MailingNotFoundException.class, () -> transferService.recordArrival(0, "100000", new Date()));
    }

    @Test
    void recordDepartureReturnsTransfer() {
        // given
        Mailing mailing = validMailing();
        PostOffice postOffice = validPostOffice();

        when(postOfficeRepository.findByIndex(any())).thenReturn(Optional.of(postOffice));

        when(mailingRepository.findById(anyLong())).thenReturn(Optional.of(mailing));

        when(transferRepository.save(any())).then(invocation -> invocation.getArgument(0));

        // when
        Transfer transfer = transferService.recordDeparture(mailing.getId(), postOffice.getIndex(), new Date());

        // then
        assertEquals(TransferType.DEPARTURE, transfer.getType());
        verify(postOfficeRepository).findByIndex(any());
        verify(mailingRepository).findById(anyLong());
        verify(transferRepository).save(any());
    }

    @Test
    void recordDepartureThrowsExceptionWhenPostOfficeNotExist() {
        // given
        when(postOfficeRepository.findByIndex(any())).thenReturn(Optional.empty());

        // then
        assertThrows(PostOfficeNotFoundException.class, () -> transferService.recordDeparture(0, "100000", new Date()));
    }


    @Test
    void recordDepartureThrowsExceptionWhenMailingNotExist() {
        // given
        when(postOfficeRepository.findByIndex(any())).thenReturn(Optional.of(validPostOffice()));

        when(mailingRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThrows(MailingNotFoundException.class, () -> transferService.recordDeparture(0, "100000", new Date()));
    }

    @Test
    void recordDeliveryReturnsTransfer() {
        // given
        Mailing mailing = validMailing();

        when(mailingRepository.findById(anyLong())).thenReturn(Optional.of(mailing));

        when(transferRepository.save(any())).then(invocation -> invocation.getArgument(0));

        // when
        Transfer transfer = transferService.recordDelivery(mailing.getId(), new Date());

        // then
        assertEquals(TransferType.DELIVERY, transfer.getType());
        verify(mailingRepository).findById(anyLong());
        verify(transferRepository).save(any());
    }

    @Test
    void recordDeliveryThrowsExceptionWhenMailingNotExist() {
        // given
        when(mailingRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThrows(MailingNotFoundException.class, () -> transferService.recordDelivery(0, new Date()));
    }
}