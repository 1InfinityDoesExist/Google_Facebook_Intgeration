package com.fb.demo.repository;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fb.demo.entity.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Integer> {

    public Tenant getTenantByName(String name);

    public Set<Tenant> findTenantByIsActive(boolean b);

}
