package com.challengeliteralura.challengeliteralura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.challengeliteralura.challengeliteralura.entity.LivroEntity;

public interface LivroRepository extends JpaRepository<LivroEntity, Long> {

    @Query("SELECT l FROM LivroEntity l WHERE l.idioma >= :idioma")
    List<LivroEntity> findForIdioma(String idioma);

}
