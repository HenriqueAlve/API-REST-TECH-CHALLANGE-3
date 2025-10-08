package fiap.gestao.hospitalar.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "enfermeiro")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Enfermeiro {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String nome;
    private String telefone;
    @Email
    private String email;

}
