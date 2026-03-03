package com.jinhongs.eternity.admin.web.controller;

import com.jinhongs.eternity.admin.web.model.dto.params.TagCreateParams;
import com.jinhongs.eternity.admin.web.model.dto.params.TagUpdateParams;
import com.jinhongs.eternity.admin.web.utils.ResultUtils;
import com.jinhongs.eternity.common.utils.result.Result;
import com.jinhongs.eternity.service.model.dto.TagCreateDTO;
import com.jinhongs.eternity.service.model.dto.TagUpdateDTO;
import com.jinhongs.eternity.service.model.vo.TagVO;
import com.jinhongs.eternity.service.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
@Tag(name = "标签管理接口")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @Operation(summary = "创建标签")
    @PostMapping("/create")
    public ResponseEntity<Result<Long>> create(@Valid @RequestBody TagCreateParams params) {
        TagCreateDTO dto = new TagCreateDTO();
        BeanUtils.copyProperties(params, dto);
        return ResultUtils.ok(tagService.createTag(dto));
    }

    @Operation(summary = "更新标签")
    @PutMapping("/update")
    public ResponseEntity<Result<Void>> update(@Valid @RequestBody TagUpdateParams params) {
        TagUpdateDTO dto = new TagUpdateDTO();
        BeanUtils.copyProperties(params, dto);
        tagService.updateTag(dto);
        return ResultUtils.ok();
    }

    @Operation(summary = "删除标签")
    @DeleteMapping("/delete")
    public ResponseEntity<Result<Void>> delete(@RequestParam Long id) {
        tagService.deleteTag(id);
        return ResultUtils.ok();
    }

    @Operation(summary = "查询所有标签（含文章数量）")
    @GetMapping("/list")
    public ResponseEntity<Result<List<TagVO>>> list() {
        return ResultUtils.ok(tagService.listTagsWithCount());
    }
}
