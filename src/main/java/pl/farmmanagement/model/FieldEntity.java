package pl.farmmanagement.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FieldEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double area;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fieldEntity",
    cascade = CascadeType.ALL)
    private List<FieldOperationEntity> operationsList;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
