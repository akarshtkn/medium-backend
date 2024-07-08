package com.java.backend.blog.entity;

import com.java.backend.jwt.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "blog")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    private String content;

    @Column(columnDefinition = "boolean default false")
    private boolean published;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    private User author;
}

//id        String   @id @default(uuid())
//title     String
//content   String
//published Boolean  @default(false)
//author    User     @relation(fields: [authorId], references: [id])
//authorId  String