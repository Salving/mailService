package salving.mailservice.transfer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import salving.mailservice.mailing.Mailing;

import java.util.List;

@Repository
public interface TransferRepository extends CrudRepository<Transfer, Long> {
    List<Transfer> findAllByMailingOrderByDate(Mailing mailing);
}
