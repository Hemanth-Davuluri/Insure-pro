package com.insurePro.document_service.Repository;

import com.insurePro.document_service.Entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentRepo extends JpaRepository<DocumentEntity,Long> {
    Optional<DocumentEntity> findByPolicyId(Long policyId);

    boolean existsByPolicyId(Long policyId);
}
