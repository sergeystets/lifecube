package lifecube.service;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import lifecube.converter.SubmissionConverter;
import lifecube.dto.Submission;
import lifecube.dto.SubmissionSearchFilter;
import lifecube.repository.dynamodb.SubmissionRepository;
import lifecube.repository.elastic.SubmissionSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubmissionService {

  private final SubmissionRepository submissionRepository;
  private final SubmissionSearchRepository submissionSearchRepository;
  private final SubmissionConverter submissionConverter;

  public Page<Submission> searchSubmissions(final SubmissionSearchFilter searchFilter) {
    final PageRequest pageRequest;

    if (searchFilter.getProperty() != null) {
      pageRequest = PageRequest.of(
          searchFilter.getPage(),
          searchFilter.getSize(),
          Sort.by(Direction.fromString(searchFilter.getDirection()), searchFilter.getProperty()));
    } else {
      pageRequest = PageRequest.of(
          searchFilter.getPage(),
          searchFilter.getSize());
    }

    if (isNotBlank(searchFilter.getText())) {
      return submissionSearchRepository.findByTextContains(searchFilter.getText(), pageRequest)
          .map(submissionConverter::convert);
    }

    return submissionSearchRepository.findAll(pageRequest).map(submissionConverter::convert);
  }

  public void save(final Submission submission) {
    if (submission.getId() != null) {
      throw new IllegalArgumentException("id must be null for a new submission");
    }
    submissionRepository.save(submissionConverter.convert(submission));
  }
}
