package lifecube.entity.elastic;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "submissions", type = "_doc", createIndex = false)
public class SubmissionEntity {

  @Id
  private String id;
  private String type;
  private String text;
}
