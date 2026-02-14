package com.insurePro.policy_service.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "policy_entity")
public class PolicyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policyId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "agent_id")
    private Long agentId;

    @Column(name = "coverage_type", nullable = false)
    private String coverageType;

    @Column(name = "premium")
    private Double premium;

    @Column(name = "tax")
    private Double tax;

    @Column(name = "status")
    private String status;

    @ElementCollection
    @CollectionTable(
            name = "policy_subscription_payload",
            joinColumns = @JoinColumn(name = "policyId")
    )
    @MapKeyColumn(name = "payload_key")
    @Column(name = "payload_value")
    private Map<String, String> payLoad;

    @Override
    public String toString() {
        return "PolicyEntity{" +
                "policyId=" + policyId +
                ", customerId=" + customerId +
                ", agentId=" + agentId +
                ", coverageType='" + coverageType + '\'' +
                ", premium=" + premium +
                ", tax=" + tax +
                ", status='" + status + '\'' +
                ", payLoad=" + payLoad +
                '}';
    }
}
