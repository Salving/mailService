package salving.mailservice.mailing;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mailing {
    @Id
    @GeneratedValue
    private Long id;

    private MailType type;
    @NotBlank
    private String destinationIndex;
    @NotBlank
    private String destinationAddress;
    @NotBlank
    private String destinationName;
}
