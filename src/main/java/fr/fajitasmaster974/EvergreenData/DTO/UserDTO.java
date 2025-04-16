package fr.fajitasmaster974.EvergreenData.DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fr.fajitasmaster974.EvergreenData.Entities.SubjectDeputy;
import fr.fajitasmaster974.EvergreenData.Entities.User;
import fr.fajitasmaster974.EvergreenData.Entities.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private String email;
    private Role role;
    private String lastName;
    private String firstName;
    private Integer id;

    public UserDTO(User user) {
        this.email = user.getEmail();
        this.role = user.getRole();
        this.id = user.getId();
        this.lastName = user.getLastName();
        this.firstName = user.getFirstName();
    }

    public static List<UserDTO> fromList(List<User> users) {
        List<UserDTO> userDTOs = new ArrayList<UserDTO>();
        for (User user : users) {
            userDTOs.add(new UserDTO(user));
        }
        return userDTOs;
    }

    public static List<UserDTO> fromList(Set<SubjectDeputy> users) {
        List<UserDTO> userDTOs = new ArrayList<UserDTO>();
        if (users == null) {
            return userDTOs;
        }
        for (SubjectDeputy user : users) {
            userDTOs.add(new UserDTO(user.getDeputy()));
        }
        return userDTOs;
    }
}
