package lifecube.dto;

import lombok.Data;

@Data
public class SubmissionSearchFilter {

  private String text;
  private int page = 0;
  private int size = 5;
  private String property;
  private String direction;
}