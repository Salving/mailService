package salving.mailservice.mailing;

import lombok.AllArgsConstructor;
import lombok.Data;
import salving.mailservice.transfer.Transfer;
import salving.mailservice.transfer.TransferType;

import java.util.List;

@Data
@AllArgsConstructor
public class MailingStatus {
    private long mailingId;
    private TransferType status;
    private List<Transfer> history;
}
