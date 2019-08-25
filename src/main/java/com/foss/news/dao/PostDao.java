package com.foss.news.dao;

import com.foss.news.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDao extends JpaRepository<Post,Long> {

    @Query(nativeQuery = true,value = "select * from post as p where p.category_id= :id")
    public List<Post> getPostByCatId(@Param("id") long id);
}
