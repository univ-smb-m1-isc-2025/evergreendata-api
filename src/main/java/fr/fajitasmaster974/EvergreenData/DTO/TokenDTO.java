package fr.fajitasmaster974.EvergreenData.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO {
    private UserDTO user;
    private String token;
}