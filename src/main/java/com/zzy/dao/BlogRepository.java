package com.zzy.dao;

import com.zzy.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog,Long> , JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.recommend = true")
    List<Blog> listBlogTop(Pageable pageable);


    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> listBlog(String query, Pageable pageable);


    @Transactional
    @Modifying
    @Query("update Blog b set b.views = b.views + 1 where b.id = ?1")
    int updateViews(Long id);

    @Query(value = "select date_format(b.update_time,'%Y') as year from t_blog b group by year desc;", nativeQuery = true)
    List<String> findGroupYear();

    @Query(value = "select * from t_blog b where date_format(b.update_time,'%Y') = ?1", nativeQuery = true)
    List<Blog> findByYear(String year);

}
