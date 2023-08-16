package salving.mailservice.mailing;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import salving.mailservice.postoffice.PostOffice;
import salving.mailservice.postoffice.PostOfficeNotFoundException;
import salving.mailservice.postoffice.PostOfficeRepository;
import salving.mailservice.transfer.Transfer;
import salving.mailservice.transfer.TransferRepository;
import salving.mailservice.transfer.TransferType;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MailingService {
    private PostOfficeRepository postOfficeRepository;
    private MailingRepository mailingRepository;
    private TransferRepository transferRepository;

    public Transfer registerMailing(Mailing mailing, String postOfficeIndex, Date date) {
        PostOffice postOffice = postOfficeRepository.findByIndex(postOfficeIndex)
                .orElseThrow(() -> new PostOfficeNotFoundException("Post office not found: %s".formatted(postOfficeIndex)));

        Mailing saved = mailingRepository.save(mailing);

        Transfer transfer = Transfer.builder()
                .index(postOffice.getIndex())
                .mailing(saved)
                .type(TransferType.REGISTRATION)
                .date(date)
                .build();

        return transferRepository.save(transfer);
    }

    public MailingStatus getStatus(long mailingId) {
        Mailing mailing = mailingRepository.findById(mailingId)
                .orElseThrow(() -> new MailingNotFoundException("Mailing not found: %s".formatted(mailingId)));

        List<Transfer> transfers = transferRepository.findAllByMailingOrderByDate(mailing);
        Transfer lastTransfer = transfers.get(transfers.size() - 1);
        MailingStatus mailingStatus = new MailingStatus(mailingId, lastTransfer.getType(), transfers);

        return mailingStatus;
    }

    public Mailing getMailing(long mailingId) {
        return mailingRepository.findById(mailingId)
                .orElseThrow(() -> new MailingNotFoundException("Mailing not found: %s".formatted(mailingId)));
    }
}

