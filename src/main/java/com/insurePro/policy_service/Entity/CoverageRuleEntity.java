package com.insurePro.policy_service.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "coverage_rule_entity")
public class CoverageRuleEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coverage_type", nullable = false)
    private String coverageType;

    @ElementCollection
    @CollectionTable(
            name = "coverage_rule_required_fields",
            joinColumns = @JoinColumn(name = "coverage_rule_id")
    )
    @Column(name = "required_field")
    private List<String> requiredFields;

    @Column(name = "premium")
    private Double premium;

    @Column(name = "tax")
    private Double tax;

}
