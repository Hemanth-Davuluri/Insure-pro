package com.insurePro.policy_service.BussinessRules;

import com.insurePro.policy_service.DTO.Customer;
import com.insurePro.policy_service.Entity.CoverageRuleEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BusinessRulesEngine {

    public Double validatingCoverageType(Customer customer, CoverageRuleEntity rule){

        double dynamicPremium =0.0;
        switch(rule.getCoverageType()){
            case "HOME_Policy":
                Double homeRiskFactor =homeRiskFactor(customer.getPayLoad());
                dynamicPremium = PremiumCalculation(rule.getTax(),homeRiskFactor,rule.getPremium());
                break;
            case "VEHICLE_Policy":
                Double vehicleRiskFactor =vehicleRiskFactor(customer.getPayLoad());
                dynamicPremium = PremiumCalculation(rule.getTax(),vehicleRiskFactor,rule.getPremium());
                break;
            case "LIFE_Policy":
                Double lifeRiskFactor =lifeRiskFactor(customer.getPayLoad());
                dynamicPremium = PremiumCalculation(rule.getTax(),lifeRiskFactor,rule.getPremium());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + rule.getCoverageType());
        }
        return dynamicPremium;
    }

    private static Double PremiumCalculation(Double tax, Double riskFactor, Double premium) {
        Double dynamic_premium=(premium*riskFactor)+((tax/100)*premium);
        return dynamic_premium;
    }


    public static Double homeRiskFactor(Map<String, String> payLoad){
        int age = Integer.parseInt((String) payLoad.get("propertyAge"));
        if(age <2) return 1.1;
        else if (age>2 && age<5) return 1.25;
        else if (age>5) return  1.5;
        else return 1.75;
    }

    public static Double vehicleRiskFactor(Map<String, String> payLoad){
        int age = Integer.parseInt((String) payLoad.get("vehicleAge"));
        if(age <2) return 1.1;
        else if (age>2 && age<5) return 1.25;
        else if (age>5) return  1.5;
        else return 1.75;
    }
    public static Double lifeRiskFactor(Map<String, String> payLoad){
        int age = Integer.parseInt((String) payLoad.get("age"));
        if(age <18) return 1.1;
        else if (age>18 && age<30) return 1.25;
        else if (age>30 && age<40) return  1.5;
        else if (age>40 && age<50) return 1.75;
        else return 2.0;
    }



//   just for validation of fields

//    public static boolean validationOfRequiredFields(Customer customer,CoverageRuleEntity entity){
//        List<String> required_fields= entity.getRequiredFields();
//        Map<String,Object> cover= customer.getPayLoad();
//        cover.entrySet().stream()
//                .filter(e -> e.getValue()==null && ! required_fields.contains(e.getKey()))
//                .findAny()
//                .ifPresent(e-> {
//                    try {
//                        throw new Exception("value not found"+e.getKey());
//                    } catch (Exception ex) {
//                        throw new RuntimeException(ex);
//                    }
//                });
//        return true;
//    }
}
