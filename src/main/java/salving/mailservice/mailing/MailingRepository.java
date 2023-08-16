package salving.mailservice.mailing;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailingRepository extends CrudRepository<Mailing, Long> {
    @Override
    Optional<Mailing> findById(Long id);
}
