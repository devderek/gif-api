package com.derek.gifapi.entitys;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="USERS")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="username")
    @NotNull
    private String username;

    @Column(name="password")
    @NotNull
    private String password;
}
