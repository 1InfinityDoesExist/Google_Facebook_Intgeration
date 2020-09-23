package com.fb.demo.entity;

import java.io.Serializable;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity(name = "Tenant")
@lombok.Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "tenant", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Tenant implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(name = "name", unique = true, nullable = false, updatable = false)
  private String name;
  private String description;
  private String version;
  private String url;
  private boolean isActive;
  private String organizationName;
  private String registrationNumber;
  private String website;
  private String tenantType;
}
