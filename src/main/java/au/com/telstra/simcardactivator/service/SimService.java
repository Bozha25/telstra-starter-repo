package au.com.telstra.simcardactivator.service;

import au.com.telstra.simcardactivator.entity.SimCard;
import au.com.telstra.simcardactivator.entity.SimCardDTO;
import au.com.telstra.simcardactivator.entity.SimRequest;
import au.com.telstra.simcardactivator.exception.CustomException;
import au.com.telstra.simcardactivator.repository.SimCardRepo;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SimService {

    private SimCardRepo repo;
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String SERVICE_URL = "http://localhost:8444/actuate";

    public SimService(SimCardRepo repo){
        this.repo = repo;
    }

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

        ResponseEntity<Map> response = restTemplate.exchange(SERVICE_URL, HttpMethod.POST, requestEntity,Map.class);

        boolean result = response.getBody() != null && Boolean.TRUE.equals(response.getBody().get("success"));

        if(UniqueSimCardByIccid(request.iccid)){
            SimCard simCard = new SimCard(request.iccid, request.customerEmail, result);
            repo.save(simCard);
        }

        return result;
    }

    public List<SimCard> GetAllSimCard(){
        List<SimCard> simCards = (List<SimCard>) repo.findAll();
        return  simCards;
    }

    public SimCardDTO GetSimCardById(Long simCardId){
        Optional<SimCard> simCardOptional  = repo.findById(simCardId);

        if(simCardOptional.isPresent()){
            SimCard simCard = simCardOptional.get();
            return new SimCardDTO(simCard.getIccid(), simCard.getCustomerEmail(), simCard.getActive());
        }else{
            throw new CustomException("SIM card with ID not found");
        }
    }

    public Map<String, String> GetStatusById(String iccid){
        Optional<SimCard> simCardOptional  = repo.findSimCardByIccid(iccid);
        if(simCardOptional.isPresent()){
            if(simCardOptional.get().getActive()){
                return Map.of("status", "active");
            }else{
                return Map.of("status", "inactive");
            }
        }else{
            throw new CustomException("SIM card with ICCID not found");
        }
    }

    private Boolean UniqueSimCardByIccid(String iccid){
        Optional<SimCard> simCardOptional  = repo.findSimCardByIccid(iccid);
        if(simCardOptional.isPresent()){
            throw new CustomException("SIM card with ICCID already exist.");
        }

        return true;
    }
}
