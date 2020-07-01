package com.zzy.service;

import com.zzy.dao.CommentRepository;
import com.zzy.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;


    private List<Comment> tempReplys = new ArrayList<>();

    @Transactional
    @Override
    public List<Comment> listCommentsById(Long blogId) {
        List<Comment> commentList =  commentRepository.findByBlogIdAndParentCommentNull(blogId, Sort.by(Sort.Direction.ASC,"createTime" ));
        return eachComment(commentList);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentRepository.getOne(comment.getParentComment().getId()));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    /**
     * 循环每个顶级评论节点
     * @Param Comments
     * @Return
     */
    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentsCopy = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsCopy.add(c);
        }
        //合并评论隔层到顶级评论的一代子集合
        combineChildren(commentsCopy);
        return commentsCopy;

    }

    /**
     * 所有的子孙节点合并到根节点的儿子节点
     *
     * @param comments root根节点
     */
    private void combineChildren(List<Comment> comments) {

        //对每一个根节点处理
        for (Comment comment : comments) {
            //根节点的儿子节点
            List<Comment> replys1 = comment.getComments();
            for (Comment reply : replys1) {
                recurveReply(reply);
            }
            //将孩子节点放置于根节点的comments中
            comment.setComments(tempReplys);
            //清除临时存放孩子节点的缓存
            tempReplys = new ArrayList<>();
        }

    }

    /**
     * 将孩子节点递归遍历，将自己放入tempReply
     * @param comment 孩子节点
     */
//    private void recurveReply(Comment comment) {
//        if (comment != null) {
//            tempReplys.add(comment);
//            for (Comment c : comment.getComments()) {
//                recurveReply(c);
//            }
//        }
//    }
    private void recurveReply(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getComments().size()>0) {
            List<Comment> replys = comment.getComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getComments().size()>0) {
                    recurveReply(reply);
                }
            }
        }
    }


}
