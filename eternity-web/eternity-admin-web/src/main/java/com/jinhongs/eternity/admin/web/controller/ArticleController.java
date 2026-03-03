package com.jinhongs.eternity.admin.web.controller;

import com.jinhongs.eternity.admin.web.model.converter.ControllerArticleConverter;
import com.jinhongs.eternity.admin.web.model.dto.params.ArticleCreateParams;
import com.jinhongs.eternity.admin.web.model.dto.params.ArticleUpdateParams;
import com.jinhongs.eternity.admin.web.utils.ResultUtils;
import com.jinhongs.eternity.common.utils.result.PageResult;
import com.jinhongs.eternity.common.utils.result.Result;
import com.jinhongs.eternity.service.model.vo.ArticleDetailVO;
import com.jinhongs.eternity.service.model.vo.ArticleListVO;
import com.jinhongs.eternity.service.model.vo.TagVO;
import com.jinhongs.eternity.service.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/article")
@Tag(name = "文章管理接口")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @Operation(summary = "创建文章")
    @PostMapping("/create")
    public ResponseEntity<Result<Long>> create(@Valid @RequestBody ArticleCreateParams params) {
        Long articleId = articleService.createArticle(
                ControllerArticleConverter.INSTANCE.toArticleCreateDTO(params)
        );
        return ResultUtils.ok(articleId);
    }

    @Operation(summary = "更新文章")
    @PutMapping("/update")
    public ResponseEntity<Result<Void>> update(@Valid @RequestBody ArticleUpdateParams params) {
        articleService.updateArticle(
                ControllerArticleConverter.INSTANCE.toArticleUpdateDTO(params)
        );
        return ResultUtils.ok();
    }

    @Operation(summary = "查询文章详情")
    @GetMapping("/detail")
    public ResponseEntity<Result<ArticleDetailVO>> detail(@RequestParam Long id) {
        ArticleDetailVO vo = articleService.getArticleDetail(id);
        return ResultUtils.ok(vo);
    }

    @Operation(summary = "分页查询文章列表")
    @GetMapping("/list")
    public ResponseEntity<Result<PageResult<ArticleListVO>>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResult<ArticleListVO> result = articleService.listArticles(page, size);
        return ResultUtils.ok(result);
    }

    @Operation(summary = "发布文章")
    @PutMapping("/publish")
    public ResponseEntity<Result<Void>> publish(@RequestParam Long id) {
        articleService.publishArticle(id);
        return ResultUtils.ok();
    }

    @Operation(summary = "删除文章")
    @PutMapping("/delete")
    public ResponseEntity<Result<Void>> delete(@RequestParam Long id) {
        articleService.deleteArticle(id);
        return ResultUtils.ok();
    }

    @Operation(summary = "查询所有标签")
    @GetMapping("/tags")
    public ResponseEntity<Result<List<TagVO>>> tags() {
        return ResultUtils.ok(articleService.listAllTags());
    }
}
