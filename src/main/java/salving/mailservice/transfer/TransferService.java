package salving.mailservice.transfer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import salving.mailservice.mailing.Mailing;
import salving.mailservice.mailing.MailingNotFoundException;
import salving.mailservice.mailing.MailingRepository;
import salving.mailservice.mailing.MailingStatus;
import salving.mailservice.postoffice.PostOffice;
import salving.mailservice.postoffice.PostOfficeNotFoundException;
import salving.mailservice.postoffice.PostOfficeRepository;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class TransferService {
    private TransferRepository transferRepository;
    private MailingRepository mailingRepository;
    private PostOfficeRepository postOfficeRepository;

    public Transfer recordArrival(long mailingId, String postOfficeIndex, Date date) {
        Transfer transfer = buildTransfer(postOfficeIndex, mailingId, date, TransferType.ARRIVAL);

        return transferRepository.save(transfer);
    }

    public Transfer recordDeparture(long mailingId, String postOfficeIndex, Date date) {
        Transfer transfer = buildTransfer(postOfficeIndex, mailingId, date, TransferType.DEPARTURE);

        return transferRepository.save(transfer);
    }

    public Transfer recordDelivery(long mailingId, Date date) {
        Mailing mailing = mailingRepository.findById(mailingId)
                .orElseThrow(() -> new MailingNotFoundException("Mailing not found: %s".formatted(mailingId)));

        Transfer transfer = Transfer.builder()
                .index(mailing.getDestinationIndex())
                .mailing(mailing)
                .type(TransferType.DELIVERY)
                .date(date)
                .build();


        return transferRepository.save(transfer);
    }

    private Transfer buildTransfer(String postOfficeIndex, long mailingId, Date date, TransferType departure) {
        PostOffice postOffice = postOfficeRepository.findByIndex(postOfficeIndex)
                .orElseThrow(
                        () -> new PostOfficeNotFoundException("Post office not found: %s".formatted(postOfficeIndex)));

        Mailing mailing = mailingRepository.findById(mailingId)
                .orElseThrow(() -> new MailingNotFoundException("Mailing not found: %s".formatted(mailingId)));

        return Transfer.builder()
                .index(postOffice.getIndex())
                .mailing(mailing)
                .type(departure)
                .date(date)
                .build();
    }

}
