package com.contract.domain;

import java.util.List;

public class ProductData {
    public class Data {
        private String title;
        private double[] data;
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public double[] getData() {
            return data;
        }

        public void setData(double[] data) {
            this.data = data;
        }
    }
    private List<String> titleList;
    private List<String> typeList;
    private List<Data> dataList;

    public List<String> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<String> titleList) {
        this.titleList = titleList;
    }

    public List<String> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }

    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }
}
