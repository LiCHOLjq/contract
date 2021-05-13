package com.contract.domain;

import java.util.List;

public class AgreementData {
    public class Data {
        private String year;
        private int[] data;
        private int sum;

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public int[] getData() {
            return data;
        }

        public void setData(int[] data) {
            this.data = data;
        }

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }
    }
    private List<String> yearList;
    private List<String> typeList;
    private List<Data> dataList;

    public List<String> getYearList() {
        return yearList;
    }

    public void setYearList(List<String> yearList) {
        this.yearList = yearList;
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
