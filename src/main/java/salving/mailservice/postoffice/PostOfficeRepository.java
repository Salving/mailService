package salving.mailservice.postoffice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostOfficeRepository  extends CrudRepository<PostOffice, String> {
    Optional<PostOffice> findByIndex(String index);
}
