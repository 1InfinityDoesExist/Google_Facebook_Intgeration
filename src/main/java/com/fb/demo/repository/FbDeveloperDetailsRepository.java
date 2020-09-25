package com.fb.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fb.demo.entity.FbDeveloperDetails;

@Repository
public interface FbDeveloperDetailsRepository extends JpaRepository<FbDeveloperDetails, Integer> {

    public FbDeveloperDetails findByParentTenant(Integer id);
}
