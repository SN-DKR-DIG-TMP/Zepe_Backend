// BY MOMATH NDIAYE
package com.zepebackend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Partenariat implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long idPartenariat;

    @OneToOne
    @JoinColumn(name = "idEntreprise" , referencedColumnName = "idPartner")
    private Partner entreprise;

    @OneToOne
    @JoinColumn(name = "idCommerce" , referencedColumnName = "idPartner")
    private Partner commerce;

    private boolean actif = true;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private Date dateCreation = new Date();


}
