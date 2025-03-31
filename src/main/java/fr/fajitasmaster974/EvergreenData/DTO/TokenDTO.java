package fr.fajitasmaster974.EvergreenData.DTO;

import fr.fajitasmaster974.EvergreenData.Entities.User;

public class TokenDTO {
    public UserDTO user;
    public String token;


    public TokenDTO(String token, User user) {
        this.token = token;
        this.user = new UserDTO(user);
    }
}