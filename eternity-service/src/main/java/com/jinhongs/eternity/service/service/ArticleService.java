package com.jinhongs.eternity.service.service;

import com.jinhongs.eternity.common.utils.result.PageResult;
import com.jinhongs.eternity.service.model.dto.ArticleCreateDTO;
import com.jinhongs.eternity.service.model.dto.ArticleUpdateDTO;
import com.jinhongs.eternity.service.model.vo.ArticleDetailVO;
import com.jinhongs.eternity.service.model.vo.ArticleListVO;
import com.jinhongs.eternity.service.model.vo.TagVO;

import java.util.List;

public interface ArticleService {

    /**
     * 创建文章（草稿或直接发布）
     *
     * @return 新建文章的 ID
     */
    Long createArticle(ArticleCreateDTO dto);

    /**
     * 更新文章内容及标签
     */
    void updateArticle(ArticleUpdateDTO dto);

    /**
     * 管理端查询文章详情（含草稿）
     */
    ArticleDetailVO getArticleDetail(Long id);

    /**
     * 管理端分页查询文章列表（含草稿，不含已删除）
     */
    PageResult<ArticleListVO> listArticles(int page, int size);

    /**
     * 发布文章（status 10 -> 20）
     */
    void publishArticle(Long id);

    /**
     * 软删除文章（status -> 30）
     */
    void deleteArticle(Long id);

    /**
     * 博客前台查询已发布文章详情
     */
    ArticleDetailVO getPublishedArticleDetail(Long id);

    /**
     * 博客前台分页查询已发布文章列表
     */
    PageResult<ArticleListVO> listPublishedArticles(int page, int size);

    /**
     * 查询全部标签（用于文章编辑时的标签选择）
     */
    List<TagVO> listAllTags();
}
