package salving.mailservice.postoffice;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostOfficeService {
    private PostOfficeRepository postOfficeRepository;

    public PostOffice registerPostOffice(PostOffice postOffice) {
        return postOfficeRepository.save(postOffice);
    }
}
