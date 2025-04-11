package fr.fajitasmaster974.EvergreenData.DTO.Body;

import lombok.Data;

@Data
public class SignInBody {
    private String login;

    private String password;

    private String email;
}
