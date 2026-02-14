package com.insurePro.policy_service.Controller;

import com.insurePro.policy_service.DTO.Customer;
import com.insurePro.policy_service.DTO.PolicyDTO;
import com.insurePro.policy_service.Service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/policySubscription")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @GetMapping("/getPoliciesSubscribed")
    public ResponseEntity<List<PolicyDTO>> getPolicesSubscribed() throws Exception{

        List<PolicyDTO> subscriptions = policyService.getPolicesSubscribed();
        if(subscriptions.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping("/getPoliciesSubscribedByaCustomer/{customerId}/{coverageType}")
    public ResponseEntity<PolicyDTO> getPolicesSubscribedByaCustomer(
            @PathVariable("customerId") Long customerId,
            @PathVariable("coverageType") String coverageType
            ) throws Exception{
        PolicyDTO subscriptions = policyService.getPolicesSubscribedByaCustomer(customerId,coverageType);
        if(subscriptions.getPolicyId()==null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(subscriptions);
    }

//    subscribing to policy
    /*
       * save  ==> post /subscribingToPolicy?submit=false         ==> Draft
       * save & submit ==> post /subscribingToPolicy?submit=true  ==> Active
     */
    @PostMapping("/subscribingToPolicy")
    public ResponseEntity<String> subscribingToPolicy(
            @RequestBody Customer dto,
            @RequestParam(required = false,defaultValue = "false") Boolean submit
    ) throws Exception{
        Long subscriptionId= policyService.subscribingToPolicy(dto,submit);
        return ResponseEntity.ok("Subscribed to Policy"+ subscriptionId);
    }

//    cancel to policy
    /*
        * cancel ==> post /subscribingToPolicy?cancel=true         ==> Cancelled
     */
    @DeleteMapping("/cancelPolicy/{id}")
    public ResponseEntity<String> cancelPolicy(
            @PathVariable("id") Long id,
            @RequestParam(required = false,defaultValue = "false") Boolean cancel
    ) throws Exception {

        policyService.unSubscribeToPolicy(id, cancel);
        return ResponseEntity.ok("deleted the policy"+ id);
    }

    /*
            a sample code to test the kafka
     */
//    @PutMapping("/tets")
//    public ResponseEntity documentGeneration() throws InterruptedException {
//        int range=10;
//        while (range>0){
////            System.out.println(Math.random()+", "+ Math.random());
//            policyService.generartion(Math.random()+", "+ Math.random());
//            Thread.sleep(1000);
//            range--;
//        }
//        return new ResponseEntity<>(Map.of("message","localtionUdapted"), HttpStatus.OK);
//    }
}
