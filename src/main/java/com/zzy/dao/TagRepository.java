package com.zzy.dao;

import com.zzy.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {

    Tag getByName(String tagName);

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);

}
