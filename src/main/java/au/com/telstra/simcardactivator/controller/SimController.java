package au.com.telstra.simcardactivator.controller;

import au.com.telstra.simcardactivator.entity.SimRequest;
import au.com.telstra.simcardactivator.service.SimService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sim")
public class SimController {

    private final SimService simService;

    public SimController(SimService simService){
        this.simService = simService;
    }

    @PostMapping("/activate")
    public ResponseEntity<Boolean> activeSim(@RequestBody SimRequest request){

        Boolean result = simService.ActiveSimCard(request);

        if(!result){
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }
}
