package com.example.vothanhtrung_shop;

public class DataHolder {
    public static String[] dataArray = null;

    public static void addData(String newData) {
        if (dataArray == null) {
            dataArray = new String[1];
            dataArray[0] = newData;
        } else {
            String[] newDataArray = new String[dataArray.length + 1];
            System.arraycopy(dataArray, 0, newDataArray, 0, dataArray.length);
            newDataArray[newDataArray.length - 1] = newData;
            dataArray = newDataArray;
        }
    }

    public static void removeData(int index) {
        if (dataArray == null || index < 0 || index >= dataArray.length) {
            return; // Handle invalid index or empty dataArray
        }
        String[] newDataArray = new String[dataArray.length - 1];
        System.arraycopy(dataArray, 0, newDataArray, 0, index);
        System.arraycopy(dataArray, index + 1, newDataArray, index, dataArray.length - index - 1);
        dataArray = newDataArray;
    }
}

