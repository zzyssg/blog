package com.zzy.web;

import com.zzy.po.Blog;
import com.zzy.service.BlogService;
import com.zzy.service.TagService;
import com.zzy.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 7,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTopTags(8));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(7));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 7,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         Model model,
                         @RequestParam String query){
        model.addAttribute("page", blogService.listBlog(query, pageable));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String  newBlogList(Model model) {
        List<Blog> newBlogs = blogService.listRecommendBlogTop(3);
        model.addAttribute("newBlogs", newBlogs);
        return "_fragments :: newBlogList";
    }



}
