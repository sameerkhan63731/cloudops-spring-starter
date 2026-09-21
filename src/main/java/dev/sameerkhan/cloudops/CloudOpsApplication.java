package dev.sameerkhan.cloudops;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@SpringBootApplication
public class CloudOpsApplication {
  public static void main(String[] args) { SpringApplication.run(CloudOpsApplication.class, args); }

  @RestController
  @RequestMapping("/api")
  static class OperationsController {
    private final Counter requests;
    OperationsController(MeterRegistry registry) {
      requests = Counter.builder("portfolio_requests_total").description("Processed demo requests").register(registry);
    }
    @GetMapping("/ready")
    Map<String,Object> ready() {
      requests.increment();
      return Map.of("status", "ready", "timestamp", Instant.now(), "service", "cloudops-spring-starter");
    }
    @GetMapping("/echo/{value}")
    Map<String,String> echo(@PathVariable String value, @RequestHeader(value="X-Correlation-ID", required=false) String correlationId) {
      requests.increment();
      return Map.of("value", value, "correlationId", correlationId == null ? "generated-by-gateway" : correlationId);
    }
  }
}
