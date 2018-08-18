package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskDao {
    private Long id;
    private String title;
    private String content;
}
