package qbit.entier.hostel.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String cid;

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


    @Column
    private String email;

    @Column
    private String address;

    @Column
    private Date birthday;
    
    @Column
    private String avatar;
    
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    
    public void updateNonNullFields(User updatedUser) {
        if (updatedUser.getUsername() != null) {
            this.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getPassword() != null) {
            this.setPassword(updatedUser.getPassword());
        }
        if (updatedUser.getRole() != null) {
            this.setRole(updatedUser.getRole());
        }
        if (updatedUser.getFullname() != null) {
            this.setFullname(updatedUser.getFullname());
        }
        if (updatedUser.getPhone() != null) {
            this.setPhone(updatedUser.getPhone());
        }
        if (updatedUser.getEmail() != null) {
            this.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getAddress() != null) {
            this.setAddress(updatedUser.getAddress());
        }
        if (updatedUser.getBirthday() != null) {
            this.setBirthday(updatedUser.getBirthday());
        }
        if (updatedUser.getAvatar() != null) {
            this.setAvatar(updatedUser.getAvatar());
        }
        if (updatedUser.getRoom() != null) {
            this.setRoom(updatedUser.getRoom());
        }
    }
}
