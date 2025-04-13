package fr.fajitasmaster974.EvergreenData.DTO;

import java.time.LocalDateTime;

import fr.fajitasmaster974.EvergreenData.Entities.Documentation;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocumentationDTO {
    private Integer id;

    private UserDTO author;

    private String content;

    private LocalDateTime date;

    public DocumentationDTO(Documentation doc) {
        this.id = doc.getId();
        this.author = new UserDTO(doc.getAuthor());
        this.content = doc.getContent();
        this.date = doc.getLastUpdate();
    }
}
