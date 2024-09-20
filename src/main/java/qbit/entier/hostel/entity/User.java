package qbit.entier.hostel.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;  

    @Column(nullable = false)
    private String fullname;

    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false, unique = true)
    private String CID;

    @Column
    private String email;

    @Column
    private String address;

    @Column
    private Date birthday;
    
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
