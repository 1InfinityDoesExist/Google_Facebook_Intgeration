package com.fb.demo.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
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


@Getter
@Setter
@Table(name = "email_template")
@Entity(name = "EmailTemplate")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailTemplate implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String format;
    private String subject;
    @OneToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "emailPropId", columnDefinition = "bigint", referencedColumnName = "id",
                    nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties("emailTemplate")
    private EmailProp emailProp;
}
