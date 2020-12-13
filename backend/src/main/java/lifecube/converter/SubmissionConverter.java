package lifecube.converter;

import lifecube.dto.Submission;
import lifecube.dto.SubmissionType;
import lifecube.entity.dynamodb.SubmissionEntity;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class SubmissionConverter {

  public SubmissionEntity convert(final Submission dto) {
    final SubmissionEntity entity = new SubmissionEntity();
    entity.setId(dto.getId() == null ? null : String.valueOf(dto.getId()));
    entity.setText(dto.getText());
    entity.setType(dto.getType() == null ? null : dto.getType().name());

    return entity;
  }

  public Submission convert(final lifecube.entity.elastic.SubmissionEntity entity) {
    final Submission dto = new Submission();
    dto.setId(entity.getId() == null ? null : UUID.fromString(entity.getId()));
    dto.setText(entity.getText());
    dto.setType(entity.getType() == null ? null : SubmissionType.valueOf(entity.getType()));

    return dto;
  }
}
