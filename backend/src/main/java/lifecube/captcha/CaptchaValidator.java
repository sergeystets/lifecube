package lifecube.captcha;

import lifecube.config.CaptchaSettings;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class CaptchaValidator {

  private static final String RECAPTCHA_URL_TEMPLATE =
      "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s";

  private final CaptchaSettings captchaSettings;
  private final RestTemplate restTemplate;

  private static String getClientIP(final HttpServletRequest request) {
    final String xfHeader = request.getHeader("X-Forwarded-For");
    if (xfHeader == null) {
      return request.getRemoteAddr();
    }
    return xfHeader.split(",")[0];
  }

  public void validate(final HttpServletRequest request) {
    if (!captchaSettings.isEnabled()){
      return;
    }
    log.debug("Validating request...");
    final String token = request.getHeader("Recaptcha-Token");
    final URI verifyUri = URI.create(String
        .format(RECAPTCHA_URL_TEMPLATE, captchaSettings.getSecret(), token, getClientIP(request)));
    final CaptchaResponse captchaResponse =
        restTemplate.getForObject(verifyUri, CaptchaResponse.class);
    log.debug("Google's response: {} ", captchaResponse);
    if (captchaResponse != null) {
      if (!captchaResponse.isSuccess()) {
        throw new AccessDeniedException("Captcha validation failed");
      }
    }
  }
}
