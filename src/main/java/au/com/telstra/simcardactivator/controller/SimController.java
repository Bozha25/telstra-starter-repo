package au.com.telstra.simcardactivator.controller;

import au.com.telstra.simcardactivator.entity.SimCard;
import au.com.telstra.simcardactivator.entity.SimCardDTO;
import au.com.telstra.simcardactivator.entity.SimRequest;
import au.com.telstra.simcardactivator.service.SimService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sim")
public class SimController {

    private final SimService simService;

    public SimController(SimService simService){
        this.simService = simService;
    }

    @PostMapping("/activate")
    public ResponseEntity<Boolean> activeSim(@RequestBody SimRequest request){

        boolean result = simService.activeSimCard(request);

        if(!result){
            return ResponseEntity.badRequest().body(false);
        }

        return ResponseEntity.ok(true);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SimCard>> getAllSims(){
        List<SimCard> simCards = simService.getAllSimCard();
        return ResponseEntity.ok(simCards);
    }

    @GetMapping("/{simCardId}")
    public ResponseEntity<SimCardDTO> getSimCardById(@PathVariable Long simCardId){
        SimCardDTO simCardDto = simService.getSimCardById(simCardId);
        return ResponseEntity.ok(simCardDto);
    }

    @GetMapping("/status/{iccid}")
    public ResponseEntity<Map<String, String>> getStatusById(@PathVariable String iccid){
        Map<String, String> status = simService.getStatusById(iccid);
        return ResponseEntity.ok(status);
    }
}
