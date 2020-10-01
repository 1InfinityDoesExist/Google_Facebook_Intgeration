package com.fb.demo.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Tenant")
@ToString
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "tenant", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Tenant extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
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
    private String organizationEmail;
    private String organizationMobileNumber;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = false,
                    mappedBy = "parentTenant")
    @JsonIgnoreProperties("parentTenant")
    private EmailProp emailProp;
}
