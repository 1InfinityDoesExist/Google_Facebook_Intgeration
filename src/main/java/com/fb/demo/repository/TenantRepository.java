package com.fb.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fb.demo.entity.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Integer> {

    public Tenant getTenantByName(String name);

    public Page<Tenant> findTenantByIsActive(boolean b, Pageable pageable);

}
