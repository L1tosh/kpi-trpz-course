package com.software.web;

import com.software.data.CommentRepository;
import com.software.domain.comment.CommentComposite;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentRepository commentRepository;

    @GetMapping
    public String getComments() {
        var comments = commentRepository.findAll();
        var commentComposite = new CommentComposite();

        comments.forEach(commentComposite::add);

        return commentComposite.convertToJson();
    }

}
