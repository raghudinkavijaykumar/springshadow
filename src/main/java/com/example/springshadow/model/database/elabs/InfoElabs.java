package com.example.springshadow.model.database.elabs;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
public class InfoElabs implements Serializable {
    /**
    *
    */
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private String value;
}
