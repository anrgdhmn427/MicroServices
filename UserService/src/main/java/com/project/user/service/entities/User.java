package com.project.user.service.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "micro_user")
public class User {
    @Id
    @Column(name = "ID")
    private String userId;
    @Column(name = "NAME", length = 20)
    private String name;
    @Column(name = "ABOUT")
    private String about;
    @Column(name = "ACTIVE_FLAG")
    private Short activeFlag;
    @Column(name = "EMAIL")
    private String email;

    @Transient
    private List<Rating> ratings;

}
