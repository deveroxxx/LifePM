package bakos.life_pm;

import bakos.life_pm.dto.response.BoardDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
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

    @GetMapping("/api/sandbox")
    public @ResponseBody BoardDto getSandbox() {
        BoardDto result = new BoardDto();
        System.out.println(UUID.randomUUID());
        result.setId(UUID.randomUUID());
        result.setName("Board name");
        result.setDescription("Board description");
        return result;
    }



}
