package com.yidao.platform.discovery.bean;

/**
 * 新的评论数据格式
 */
public class CommentsItem {
    /**
     * 评论的id 用来做删除接口参数
     */
    private long commentId;
    /**
     * 不知道什么用
     */
    private long memberId;
    private long ownerId;
    private String ownerName;
    private long deployId;
    private String deployName;
    private String content;

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public long getDeployId() {
        return deployId;
    }

    public void setDeployId(long deployId) {
        this.deployId = deployId;
    }

    public String getDeployName() {
        return deployName;
    }

    public void setDeployName(String deployName) {
        this.deployName = deployName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
