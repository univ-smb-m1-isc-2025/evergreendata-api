package fr.fajitasmaster974.EvergreenData.DTO.Body;

import lombok.Data;

@Data
public class SignInBody {
    private String password;

    private String email;

    private String lastName;

    private String firstName;
}
