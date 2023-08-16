package salving.mailservice.mailing;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import salving.mailservice.transfer.Transfer;

import java.util.Date;

@RestController
@AllArgsConstructor
@RequestMapping("/mailings")
public class MailingController {
    private MailingService mailingService;

    @Operation(description = "Регистрация почтового отправления")
    @Parameter(name = "date",
            description = "Формат даты: yyyy-MM-dd'T'HH:mm:ss",
            example = "2023-08-16T22:45:16",
            schema = @Schema(format = "spring.mvc.format.date"))
    @PostMapping("/")
    public Transfer registerMailing(
            @RequestBody Mailing mailing,
            @RequestParam String postOfficeIndex,
            @RequestParam Date date) {
        return mailingService.registerMailing(mailing, postOfficeIndex, date);
    }

    @Operation(description = "Просмотр статуса почтового отправления")
    @GetMapping("/{mailingId}/status")
    public MailingStatus getStatus(@PathVariable long mailingId) {
        return mailingService.getStatus(mailingId);
    }

    @Operation(description = "Просмотр деталей почтового отправления")
    @GetMapping("/{mailingId}")
    public Mailing getMailing(@PathVariable long mailingId) {
        return mailingService.getMailing(mailingId);
    }
}
