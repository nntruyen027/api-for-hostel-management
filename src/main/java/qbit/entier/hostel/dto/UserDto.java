package qbit.entier.hostel.dto;

import java.util.Date;
import java.util.Optional;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import qbit.entier.hostel.entity.Room;
import qbit.entier.hostel.entity.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {

    private Long id;

    private String role;  

    private String fullname;

    private String phone;
 
    private String CID;

    private String email;

    private String address;

    private Date birthday;

    private Room room; 
    
    public static UserDto toDto(User user) {
    	if(user == null)
    		return null;
    	return new UserDto().builder()
    			.address(user.getAddress())
    			.birthday(user.getBirthday())
    			.CID(user.getCID())
    			.email(user.getEmail())
    			.fullname(user.getFullname())
    			.id(user.getId())
    			.phone(user.getPhone())
    			.role(user.getRole())
    			.room(user.getRoom())
    			.build();
    }
    
    public static UserDto toDto(Optional<User> user) {
       	if(user == null)
    		return null;
    	return new UserDto().builder()
    			.address(user.get().getAddress())
    			.birthday(user.get().getBirthday())
    			.CID(user.get().getCID())
    			.email(user.get().getEmail())
    			.fullname(user.get().getFullname())
    			.id(user.get().getId())
    			.phone(user.get().getPhone())
    			.role(user.get().getRole())
    			.room(user.get().getRoom())
    			.build();
    }
}
