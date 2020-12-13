package lifecube.captcha;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import lombok.ToString;

@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptchaResponse {

  @JsonProperty("success")
  private boolean success;
  @JsonProperty("challenge_ts")
  private String challengeTs;
  @JsonProperty("hostname")
  private String hostname;
  @JsonProperty("score")
  private float score;
  @JsonProperty("action")
  private String action;
  @JsonProperty("error-codes")
  private ErrorCode[] errorCodes;

  @JsonProperty("success")
  public boolean isSuccess() {
    return success;
  }

  @JsonProperty("success")
  public void setSuccess(boolean success) {
    this.success = success;
  }

  @JsonProperty("challenge_ts")
  public String getChallengeTs() {
    return challengeTs;
  }

  @JsonProperty("challenge_ts")
  public void setChallengeTs(String challengeTs) {
    this.challengeTs = challengeTs;
  }

  @JsonProperty("hostname")
  public String getHostname() {
    return hostname;
  }

  @JsonProperty("hostname")
  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  @JsonProperty("error-codes")
  public ErrorCode[] getErrorCodes() {
    return errorCodes;
  }

  @JsonProperty("error-codes")
  public void setErrorCodes(ErrorCode[] errorCodes) {
    this.errorCodes = errorCodes;
  }

  @JsonProperty("score")
  public float getScore() {
    return score;
  }

  @JsonProperty("score")
  public void setScore(float score) {
    this.score = score;
  }

  @JsonProperty("action")
  public String getAction() {
    return action;
  }

  @JsonProperty("action")
  public void setAction(String action) {
    this.action = action;
  }

  @JsonIgnore
  public boolean hasClientError() {
    final ErrorCode[] errors = getErrorCodes();
    if (errors == null) {
      return false;
    }
    for (final ErrorCode error : errors) {
      switch (error) {
        case InvalidResponse:
        case MissingResponse:
        case BadRequest:
          return true;
        default:
          break;
      }
    }
    return false;
  }

  enum ErrorCode {
    MissingSecret, InvalidSecret, MissingResponse, InvalidResponse, BadRequest, TimeoutOrDuplicate;

    private static final Map<String, ErrorCode> errors =
        new ImmutableMap.Builder<String, ErrorCode>()
            .put("missing-input-secret", MissingSecret)
            .put("invalid-input-secret", InvalidSecret)
            .put("missing-input-response", MissingResponse)
            .put("bad-request", InvalidResponse)
            .put("invalid-input-response", BadRequest)
            .put("timeout-or-duplicate", TimeoutOrDuplicate).build();


    @JsonCreator
    public static ErrorCode forValue(final String value) {
      return errors.get(value.toLowerCase());
    }
  }
}