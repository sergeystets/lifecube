package lifecube.controller.api;

import lifecube.captcha.CaptchaValidator;
import lifecube.dto.Submission;
import lifecube.dto.SubmissionSearchFilter;
import lifecube.service.SubmissionService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/submission")
@RequiredArgsConstructor
public class SubmissionController {

  private final CaptchaValidator captchaValidator;
  private final SubmissionService submissionService;

  @GetMapping("/search")
  public Page<Submission> searchSubmissions(final @Valid SubmissionSearchFilter searchFilter) {
    log.info("Requesting search submissions by filter {}", searchFilter);
    return submissionService.searchSubmissions(searchFilter);
  }

  @PostMapping
  public void saveSubmission(final HttpServletRequest request,
      @RequestBody @Valid final Submission submission) {
    log.info("Saving new submission {}", submission);
    captchaValidator.validate(request);
    submissionService.save(submission);
  }
}
