package org.guestu.webfluxreactive.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.List;

@Document
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Societe {
    @Id
    private String id;
    private String nom;
    private double price;
    //@DBRef
    List<Transaction> transactions;
}
