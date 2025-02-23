package au.com.telstra.simcardactivator;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/sim")
public class SimController {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String Service_URL = "http://localhost:8444/actuate";

    @PostMapping("/activate")
    public ResponseEntity<Boolean> activeSim(@RequestBody SimRequest request){

        String iccid = request.iccid;
        String customerEmail = request.customerEmail;

        if(iccid == null || customerEmail == null){
            return ResponseEntity.badRequest().body(false);
        }

        Map<String, String> activeRequest = Map.of("iccid", iccid);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(activeRequest, header);

        ResponseEntity<Map> response = restTemplate.exchange(Service_URL, HttpMethod.POST, requestEntity,Map.class);

        boolean result = response.getBody() != null && Boolean.TRUE.equals(response.getBody().get("success"));

        System.out.println(result);

        return ResponseEntity.ok(result);
    }
}
