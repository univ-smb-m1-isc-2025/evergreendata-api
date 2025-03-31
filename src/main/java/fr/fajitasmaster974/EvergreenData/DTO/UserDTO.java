package fr.fajitasmaster974.EvergreenData.DTO;

import fr.fajitasmaster974.EvergreenData.Entities.Role;
import fr.fajitasmaster974.EvergreenData.Entities.User;

public class UserDTO {
    public String login;
    public String email;
    public Role role;

    public UserDTO(User user) {
        login = user.getLogin();
        email = user.getEmail();
        role = user.getRole();
    }
}
