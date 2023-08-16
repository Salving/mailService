package salving.mailservice.postoffice;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PostOfficeController {
    private PostOfficeService postOfficeService;

    @PostMapping("/offices")
    public PostOffice registerPostOffice(PostOffice postOffice) {
        return postOfficeService.registerPostOffice(postOffice);
    }
}
