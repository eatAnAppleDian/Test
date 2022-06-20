package com.orange.vo;

import lombok.Data;

import java.util.List;

@Data
public class MenuVo {
    private Long id;
    private Long pid;
    private String name;
    private String path;
    private String component;
    private Meta meta;
    private List<MenuVo> children;

    @Data
    public static class Meta {
        private boolean hidden;
        private String icon;
        private String title;
    }
}