package com.meongmory.meongmory.domain.diary.entity;

import com.meongmory.meongmory.domain.family.entity.Family;
import com.meongmory.meongmory.domain.family.entity.Pet;
import com.meongmory.meongmory.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class Diary extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="petId")
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="familyId")
    private Family family;

    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private Scope scope;

    @Builder
    public Diary(Pet pet, String title, String content, Scope scope) {
        this.pet = pet;
        this.title=title;
        this.content=content;
        this.scope=scope;
    }
}
