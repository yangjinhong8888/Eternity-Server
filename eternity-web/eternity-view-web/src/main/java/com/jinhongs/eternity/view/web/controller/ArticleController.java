package com.jinhongs.eternity.view.web.controller;

import com.jinhongs.eternity.common.utils.result.PageResult;
import com.jinhongs.eternity.common.utils.result.Result;
import com.jinhongs.eternity.service.model.vo.ArticleDetailVO;
import com.jinhongs.eternity.service.model.vo.ArticleListVO;
import com.jinhongs.eternity.service.model.vo.TagVO;
import com.jinhongs.eternity.service.service.ArticleService;
import com.jinhongs.eternity.service.service.TagService;
import com.jinhongs.eternity.view.web.security.annotation.PassAll;
import com.jinhongs.eternity.view.web.utils.ResultUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
@Tag(name = "博客文章接口")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final TagService tagService;

    @PassAll
    @Operation(summary = "查询已发布文章详情")
    @GetMapping("/detail")
    public ResponseEntity<Result<ArticleDetailVO>> detail(@RequestParam Long id) {
        ArticleDetailVO vo = articleService.getPublishedArticleDetail(id);
        return ResultUtils.ok(vo);
    }

    @PassAll
    @Operation(summary = "分页查询已发布文章列表")
    @GetMapping("/list")
    public ResponseEntity<Result<PageResult<ArticleListVO>>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResult<ArticleListVO> result = articleService.listPublishedArticles(page, size);
        return ResultUtils.ok(result);
    }

    @PassAll
    @Operation(summary = "按标签筛选已发布文章")
    @GetMapping("/list-by-tag")
    public ResponseEntity<Result<PageResult<ArticleListVO>>> listByTag(
            @RequestParam Long tagId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResult<ArticleListVO> result = articleService.listPublishedArticlesByTag(tagId, page, size);
        return ResultUtils.ok(result);
    }

    @PassAll
    @Operation(summary = "查询所有标签（含文章数量）")
    @GetMapping("/tags")
    public ResponseEntity<Result<List<TagVO>>> tags() {
        return ResultUtils.ok(tagService.listTagsWithCount());
    }
}
