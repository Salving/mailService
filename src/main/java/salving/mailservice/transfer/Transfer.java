package salving.mailservice.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import salving.mailservice.mailing.Mailing;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Transfer {

    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne
    @JsonIgnore
    private Mailing mailing;
    @NotBlank
    private String index;
    private TransferType type;
    private Date date;
}

