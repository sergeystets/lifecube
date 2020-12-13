package lifecube.dto;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Submission {

  private UUID id;
  @NotNull
  private SubmissionType type;
  @Size(min = 4, max = 1000)
  private String text;
}
