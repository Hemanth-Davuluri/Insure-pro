package com.insurepro.claim_service.Entity;

import jakarta.persistence.*;
import lombok.Getter; 
import lombok.Setter; 

import java.time.Instant; 

@Entity 
@Getter 
@Setter 
@Table(name = "Claim_entity") 
public class ClaimEntity { 

 @Id 
 @GeneratedValue(strategy = GenerationType.IDENTITY) 
 private Long id; 

 @Column(name = "policy_id", nullable = false) 
 private Long policyId; 

 @Column(name = "amount", nullable = false) 
 private Double amount; 

 @Column(name = "description", nullable = true) 
 private String description; 

 @Column(name ="image_path",nullable = false) 
 private String imagePath; 

 @Column(name = "status") 
 private String status; 

 @Column(name = "createdDate") 
 private Instant date; 

}