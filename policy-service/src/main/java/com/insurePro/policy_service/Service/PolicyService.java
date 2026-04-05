package com.insurePro.policy_service.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurePro.policy_service.BussinessRules.BusinessRulesEngine;
import com.insurePro.policy_service.DTO.Customer;
import com.insurePro.policy_service.DTO.PolicyDTO;
import com.insurePro.policy_service.Entity.CoverageRuleEntity;
import com.insurePro.policy_service.Entity.PolicyEntity;
import com.insurePro.policy_service.Enums.PolicyEvents;
import com.insurePro.policy_service.Enums.PolicyStates;
import com.insurePro.policy_service.MapperClasses.CustomerSubscriptionMapper;
import com.insurePro.policy_service.Repository.CoverageRepository;
import com.insurePro.policy_service.Repository.PolicySubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PolicyService {

    @Autowired
    private PolicySubRepository policySubRepository;

    @Autowired
    private CoverageRepository coverageRepository;

    @Autowired
    private CustomerSubscriptionMapper mapper;

    @Autowired
    private BusinessRulesEngine businessRulesEngine;

    @Autowired
    private StateMachineFactory<PolicyStates, PolicyEvents> stateMachineFactory;


    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    //    retrieving customers who subscribed to policy
    public List<PolicyDTO> getPolicesSubscribed() throws Exception {
        List<PolicyEntity> all = policySubRepository.findAll();
        return mapper.toDTOList(all);
    }

    //    subscribing to policy
    public Long subscribingToPolicy(Customer customer, Boolean submit) throws Exception {

        CoverageRuleEntity rule = coverageRepository.findByCoverageType(customer.getCoverageType());
        Double dynamic_premium = businessRulesEngine.validatingCoverageType(customer, rule);

        PolicyEntity entity = new PolicyEntity();
        ObjectMapper mapper= new ObjectMapper();

        if (customer.getPolicyId() != null) {
            entity.setPolicyId(customer.getPolicyId());
        }
        entity.setCustomerId(customer.getCustomerId());
        entity.setAgentId(13892415L);
        entity.setCoverageType(customer.getCoverageType());
        entity.setPremium(dynamic_premium);
        entity.setTax(rule.getTax());
        entity.setStatus(PolicyStates.DRAFT.name());
        entity.setPayLoad(customer.getPayLoad());
        PolicyEntity savedPolicy = policySubRepository.save(entity);
        if (submit) {
            StateMachine<PolicyStates, PolicyEvents> stateMachine = stateMachineFactory.getStateMachine(savedPolicy.getPolicyId().toString());
            stateMachine.stop();
            stateMachine.getStateMachineAccessor()
                    .doWithAllRegions(access -> access.resetStateMachine(
                            new DefaultStateMachineContext<>(
                                    PolicyStates.valueOf(savedPolicy.getStatus()), // Current state from DB
                                    null, null, null
                            )
                    ));
//            the 2 commands are depreciated
            stateMachine.start();
            stateMachine.sendEvent(PolicyEvents.SUBMIT);
            savedPolicy.setStatus(stateMachine.getState().getId().name());
            return policySubRepository.save(savedPolicy).getPolicyId();
        }
        System.out.println(savedPolicy.toString());

        Map<String, Object> map = mapper.convertValue(savedPolicy, Map.class);
        kafkaTemplate.send("document",map);
//            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("document",entity.getPolicyId().toString(),policyDetails);
//            future.whenComplete((result, ex) -> {
//                if (ex == null) {
//                    // Message sent successfully
//                    System.out.println("Sent message with offset: " + result.getRecordMetadata().offset());
//                } else {
//                    // Handle error
//                    System.err.println("Failed to send message: " + ex.getMessage());
//                }
//            });

        return savedPolicy.getPolicyId();
    }

    //    unsubscribing to policy
    public String unSubscribeToPolicy(Long id, Boolean cancel) throws Exception {

        PolicyEntity policyFound = policySubRepository.findById(id).orElseThrow(() -> new RuntimeException("Policy not found"));

        if (!cancel) {
            throw new Exception("Make cancel=true in request param to delete the policy");
        }
        StateMachine<PolicyStates, PolicyEvents> stateMachine = stateMachineFactory.getStateMachine(policyFound.getPolicyId().toString());
        stateMachine.stop();
        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(access -> access.resetStateMachine(
                        new DefaultStateMachineContext<>(
                                PolicyStates.valueOf(policyFound.getStatus()), // Current state from DB
                                null, null, null
                        )
                ));
        stateMachine.start();
        stateMachine.sendEvent(PolicyEvents.CANCEL);
        policyFound.setStatus(stateMachine.getState().getId().name());
        return policySubRepository.save(policyFound).getPolicyId().toString();

    }

    public PolicyDTO getPolicesSubscribedByaCustomer(Long customerId, String coverageType) {
        PolicyEntity all = policySubRepository.findByCustomerIdAndCoverageType(customerId,coverageType);
        return mapper.toDTO(all);
    }




    /*
        ** a sample service method to test the kafka
     */

//    public  boolean generartion(String location){
//
//        kafkaTemplate.send(AppConstant.DOCUMENT,location);
//        return true;
//    }



//        if(policySubRepository.existsById(id)){
//            if(cancel) policySubRepository.deleteById(id);
//            else throw new Exception("Make cancel=true in request param to delete the policy");
//        }
//        else throw new Exception("Coverage Type not found");


}
