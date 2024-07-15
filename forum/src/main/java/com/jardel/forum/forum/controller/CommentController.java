package com.jardel.forum.controller;

import com.jardel.forum.model.Comment;
import com.jardel.forum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.findAll();
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        return commentService.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment comment, @AuthenticationPrincipal UserDetails userDetails) {
        // Supondo que o nome de usuário está no UserDetails
        comment.setUser(new User(userDetails.getUsername()));
        return commentService.save(comment);
    }

    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        Comment existingComment = commentService.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        existingComment.setContent(comment.getContent());
        return commentService.save(existingComment);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteById(id);
    }
}
