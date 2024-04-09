package com.group21.chinasoft_project_barbie_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResidentInfoToFrontDTO {
//    private String id;   //用户的唯一标识
    private int code;   //操作的结果（0代表失败，1代表成功）
    private List<DataItem> data;
    // data 字段，它是一个包含多个 DataItem 对象的列表。
    // 每个 DataItem 对象包含 type 和 value 字段。

    @Data
    public static class DataItem {   //单个数据条目
        private String type;
        private double value;

        public DataItem(String type, double value) {
            this.type = type;
            this.value = value;
        }
    }
}
