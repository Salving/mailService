package salving.mailservice.transfer;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@AllArgsConstructor
@RequestMapping("/transfers")
public class TransferController {
    private TransferService transferService;

    @Operation(description = "Регистрация прибытия в промежуточное почтовое отделение")
    @Parameter(name = "date",
            description = "Формат даты: yyyy-MM-dd'T'HH:mm:ss",
            example = "2023-08-16T22:45:16",
            schema = @Schema(format = "spring.mvc.format.date"))
    @PostMapping("/arrivals")
    public Transfer recordArrival(
            @RequestParam long mailingId, @RequestParam String postOfficeIndex, @RequestParam Date date) {
        return transferService.recordArrival(mailingId, postOfficeIndex, date);
    }

    @Operation(description = "Регистрация убытия из почтового отделения")
    @Parameter(name = "date",
            description = "Формат даты: yyyy-MM-dd'T'HH:mm:ss",
            example = "2023-08-16T22:45:16",
            schema = @Schema(format = "spring.mvc.format.date"))
    @PostMapping("/departures")
    public Transfer recordDeparture(
            @RequestParam long mailingId, @RequestParam String postOfficeIndex, @RequestParam Date date) {
        System.out.println(date);
        return transferService.recordDeparture(mailingId, postOfficeIndex, date);
    }
    @Operation(description = "Регистрация получения почтового отправления")
    @Parameter(name = "date",
            description = "Формат даты: yyyy-MM-dd'T'HH:mm:ss",
            example = "2023-08-16T22:45:16",
            schema = @Schema(format = "spring.mvc.format.date"))
    @PostMapping("/deliveries")
    public Transfer recordDelivery(@RequestParam long mailingId, @RequestParam Date date) {
        return transferService.recordDelivery(mailingId, date);
    }
}
