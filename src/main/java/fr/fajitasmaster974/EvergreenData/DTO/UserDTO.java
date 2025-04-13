package fr.fajitasmaster974.EvergreenData.DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import fr.fajitasmaster974.EvergreenData.Entities.SubjectDeputy;
import fr.fajitasmaster974.EvergreenData.Entities.User;
import fr.fajitasmaster974.EvergreenData.Entities.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private String login;
    private String email;
    private Role role;
    private Integer id;

    public UserDTO(User user) {
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.id = user.getId();
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
