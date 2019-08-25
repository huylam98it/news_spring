package com.foss.news.dao;

import com.foss.news.dto.CategoryDTO;
import com.foss.news.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryDao extends JpaRepository<Category,Long> {

    @Query(nativeQuery = true,value = "select cat.id,cat.name,cat.created,count(*) as count from category as cat left join" +
            " post on cat.id=post.category_id group by cat.id,cat.name,cat.created")
    public List<CategoryDTO> getListCategoryDTO();
}
