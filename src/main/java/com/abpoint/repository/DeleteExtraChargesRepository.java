package com.abpoint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abpoint.model.DeletedExtraCharges;

@Repository
public interface DeleteExtraChargesRepository extends JpaRepository<DeletedExtraCharges, Long> {
}
