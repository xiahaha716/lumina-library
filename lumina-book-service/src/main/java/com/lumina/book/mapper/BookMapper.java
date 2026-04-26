package com.lumina.book.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lumina.book.entity.Book;
import org.apache.ibatis.annotations.Mapper;

// 加上 @Mapper 注解，Spring 就会自动把它接管
// 继承 BaseMapper<Book> 后，就自动拥有了对 book 表的几十种现成操作方法
@Mapper
public interface BookMapper extends BaseMapper<Book> {
}
