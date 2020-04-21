package com.deepoove.poi.data;

import java.util.ArrayList;
import java.util.List;

public class CustomTableListRenderData implements RenderData {

  private List<CustomTableRenderData> tableList;

  public static CustomTableListRenderData buid(CustomTableRenderData... table) {
    List<CustomTableRenderData> list = new ArrayList<>();
    if (null != table) {
      for (CustomTableRenderData tableData : table) {
        list.add(tableData);
      }
      return new CustomTableListRenderData(list);
    }
    return null;
  }

  public CustomTableListRenderData(List<CustomTableRenderData> tableList) {
    this.tableList = tableList;
  }

  public List<CustomTableRenderData> getTableList() {
    return this.tableList;
  }

  public void setTableList(List<CustomTableRenderData> tableList) {
    this.tableList = tableList;
  }
}
