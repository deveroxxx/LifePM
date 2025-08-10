package bakos.life_pm;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class SandboxController {

    private final AtomicInteger requestCounter = new AtomicInteger(0);

    @GetMapping("/status")
    public String getStatus() {
        int count = requestCounter.incrementAndGet();
        if (count % 2 == 0) {
            return "{'status' : 'TRIGGER_TEXT'}";
        } else {
            return "{'status' : 'IDLE'}";
        }
    }



}
