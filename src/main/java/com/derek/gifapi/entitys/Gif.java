package com.derek.gifapi.entitys;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="GIFS")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class Gif implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="collection_id")
    @NotNull
    private Long collectionId;

    @Column(name="giphy_id")
    @NotNull
    private String giphyId;

    @Column(name="giphy_url")
    @NotNull
    private String giphyUrl;
}
