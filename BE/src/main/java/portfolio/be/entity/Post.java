package portfolio.be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String tagline;
    private String preview;
    private String image;
    private Date date;

    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;
}
