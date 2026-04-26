package com.lumina.book.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

// 告诉 MyBatis-Plus 这个类对应数据库里的 book 表
@TableName("book")
public class Book {

    // 告诉 MyBatis-Plus 这是主键，且是自增的
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String author;
    private Integer inventory;
    private String description;

    // --- 下面是标准的 Getter 和 Setter 方法 ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Integer getInventory() { return inventory; }
    public void setInventory(Integer inventory) { this.inventory = inventory; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
