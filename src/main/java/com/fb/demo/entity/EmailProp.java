package com.fb.demo.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name = "EmailProp")
@Table(name = "email_prop")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailProp implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String host;
    private String userName;
    private String email;
    private String password;
    private int sslPort;
    private int port;
    @OneToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "parentTenantId", columnDefinition = "bigint", referencedColumnName = "id",
                    nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties("emailProp")
    private Tenant parentTenant;
    private String application;
    private String provider;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH}, orphanRemoval = false,
                    mappedBy = "emailProp")
    @JsonIgnoreProperties("emailProp")
    private EmailTemplate emailTemplate;
}
