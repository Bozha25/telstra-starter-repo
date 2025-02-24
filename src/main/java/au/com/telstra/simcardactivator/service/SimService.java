package au.com.telstra.simcardactivator.service;

import au.com.telstra.simcardactivator.entity.SimCard;
import au.com.telstra.simcardactivator.entity.SimRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SimService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String Service_URL = "http://localhost:8444/actuate";

    public Boolean ActiveSimCard(SimRequest request){

        String iccid = request.iccid;
        String customerEmail = request.customerEmail;

        if(iccid == null || customerEmail == null){
            return false;
        }

        Map<String, String> activeRequest = Map.of("iccid", iccid);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(activeRequest, header);

        ResponseEntity<Map> response = restTemplate.exchange(Service_URL, HttpMethod.POST, requestEntity,Map.class);

        boolean result = response.getBody() != null && Boolean.TRUE.equals(response.getBody().get("success"));

        SimCard simCard = new SimCard(request.iccid, request.customerEmail, result);
        //System.out.println(result);
        System.out.println(simCard);


        return result;
    }
}
