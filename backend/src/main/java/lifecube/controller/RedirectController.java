package lifecube.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectController {

  // Forwards all routes to FrontEnd except: '/', '/index.html', '/api', '/api/**'
  // Required because of 'mode: history' usage in frontend routing
  @RequestMapping(value = "{_:^(?!index\\.html|api).*$}")
  public String redirectApi() {
    return "forward:/";
  }
}
