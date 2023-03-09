// BY MOMATH NDIAYE
package com.zepebackend.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jeton implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJeton;
    private double montantEmploye;
    private double montantEntreprise;
    @OneToOne
    @JoinColumn(name = "idEntreprise")
    private Partner partner;

}
