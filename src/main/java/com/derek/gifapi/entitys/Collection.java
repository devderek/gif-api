package com.derek.gifapi.entitys;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="COLLECTIONS")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class Collection implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="user_id")
    @NotNull
    private Long userId;

    @Column(name="collection_name")
    @NotNull
    private String collectionName;
}
