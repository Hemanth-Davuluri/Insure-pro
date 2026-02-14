package com.insurePro.policy_service.Controller;


import com.insurePro.policy_service.DTO.CoverageRule;
import com.insurePro.policy_service.Entity.CoverageRuleEntity;
import com.insurePro.policy_service.Service.CoverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/CoverageRules")
public class CoverageController {

    @Autowired
    private CoverageService coverageService;


    //   Retrieving all coverage rule
    @GetMapping("id/{id}")
    public ResponseEntity<CoverageRule> getCoverage(@PathVariable("id") Long id) throws Exception{
        CoverageRule rule =coverageService.getCoverageRule(id);
        return ResponseEntity.ok().body(rule);
    }

    //  Retrieving coverage rules
    @GetMapping("/getRules")
    public ResponseEntity<List<CoverageRule>> getCoverage() throws Exception{
        List<CoverageRule> coverageRuleList=coverageService.getCoverageRulesList();
        return ResponseEntity.ok().body(coverageRuleList);
    }

    //  creating new policy
    @PostMapping("/createCoverageRule")
    public ResponseEntity<String> createCoverageRule(@RequestBody CoverageRule coverageRule) throws Exception{
        Long coverageId= coverageService.createCoverageRule(coverageRule);

        return ResponseEntity.ok().body("created a policy: "+coverageId);
    }

    //  deleting policy
    @DeleteMapping("delete/policy")
    public ResponseEntity<String> deleteCoverageRule(@RequestBody CoverageRule coverageRule) throws Exception{
        Long coverageId= coverageService.deleteCoverageRule(coverageRule);
        return new ResponseEntity<>("deleted the policy: "+coverageId,HttpStatus.OK);
    }

    //  updating policy
    @PutMapping("update/{id}")
    public ResponseEntity<String> updateCoverageRule(@PathVariable("id") Long id,@RequestBody CoverageRule updatedCoverage) throws Exception{
        if (coverageService.searchCoverageRule(id)){
            CoverageRule updatedCoverageRule =coverageService.updateCoverageRule(updatedCoverage);

            return  new ResponseEntity<>("updated coverage Rule "+updatedCoverageRule.toString(), HttpStatus.OK);

        }
        return new ResponseEntity<>("Coverage not Found",HttpStatus.NOT_FOUND);
    }

    //    retrieving required Fields
    @GetMapping("type/{coverageType}")
    public ResponseEntity<List<String>> getRequiredFields(@PathVariable String coverageType) {
        CoverageRule rule = coverageService.getCoverageRuleFields(coverageType);
        return ResponseEntity.ok(rule.getRequiredFields());
    }





}
