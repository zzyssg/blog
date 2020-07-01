package com.zzy.service;

import com.zzy.po.Comment;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentsById(Long blogId);

    Comment saveComment(Comment comment);

}
