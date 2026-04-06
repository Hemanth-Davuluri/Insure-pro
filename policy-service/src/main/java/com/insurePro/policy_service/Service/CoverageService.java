package com.insurePro.policy_service.Service;

import com.insurePro.policy_service.DTO.CoverageRule;
import com.insurePro.policy_service.Entity.CoverageRuleEntity;
import com.insurePro.policy_service.MapperClasses.CoverageRuleMapper;
import com.insurePro.policy_service.Repository.CoverageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CoverageService {


    @Autowired
    private CoverageRepository coverageRepository;

    @Autowired
    private CoverageRuleMapper coverageRuleMapper;

//  Retrieving coverage rule by claimId
    public CoverageRule getCoverageRule(Long id) throws Exception {

        return coverageRuleMapper.coverageRuleEntityToCoverageRule(coverageRepository.findById(id).orElseThrow(()->new Exception("coverage not found")));
    }

//  creating new Coverage Rule
    public Long createCoverageRule(CoverageRule coverageRule) {
        CoverageRuleEntity entity = coverageRuleMapper.coverageRuleToCoverageRuleEntity(coverageRule);

        return coverageRepository.save(entity).getId();
    }

//    delete Coverage rule
    public Long deleteCoverageRule(CoverageRule coverageRule) throws Exception {

        CoverageRuleEntity entity = coverageRuleMapper.coverageRuleToCoverageRuleEntity(coverageRule);
        CoverageRuleEntity coverage = coverageRepository.findById(entity.getId())
                .orElseThrow(() -> new Exception("Coverage not found"));
        coverageRepository.delete(coverage);

        return entity.getId();
    }

//  retrieving all coverage rules
    public List<CoverageRule> getCoverageRulesList() {
        List<CoverageRuleEntity> entities = coverageRepository.findAll();
        return coverageRuleMapper.coverageRuleEntityToCoverageRuleList(entities);
    }

//  searching for coverage rule whether it exist or not
    public boolean searchCoverageRule(Long id) {
        return coverageRepository.existsById(id);
    }

//  updating the coverage Rule
    public CoverageRule updateCoverageRule(CoverageRule updatedCoverage) {
        CoverageRuleEntity entity =  coverageRepository.save(coverageRuleMapper.coverageRuleToCoverageRuleEntity(updatedCoverage));
        return coverageRuleMapper.coverageRuleEntityToCoverageRule(entity);
    }

//  required Fields
    public CoverageRule getCoverageRuleFields(String coverageType) {
        CoverageRuleEntity rule = coverageRepository.findByCoverageType(coverageType);
        return coverageRuleMapper.coverageRuleEntityToCoverageRule(rule);
    }

}

