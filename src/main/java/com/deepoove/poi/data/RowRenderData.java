/*
 * Copyright 2014-2020 Sayi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.data;

import com.deepoove.poi.data.style.TableStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 表格行数据
 *
 * @author Sayi
 * @version 1.3.0
 */
public class RowRenderData implements RenderData {

  private List<CellRenderData> cells;

  /**
   * 行级样式，应用到该行所有单元格：背景色、行文字对齐方式
   */
  private TableStyle rowStyle;

  public RowRenderData() {
  }

  public RowRenderData(List<CellRenderData> cellDatas) {
    this.cells = cellDatas;
  }

  public static RowRenderData build(String... cellStr) {
    List<TextRenderData> cellDatas = new ArrayList<TextRenderData>();
    if (null != cellStr) {
      for (String col : cellStr) {
        cellDatas.add(new TextRenderData(col));
      }
    }
    return new RowRenderData(cellDatas, null);
  }

  public static RowRenderData build(TextRenderData... cellData) {
    return new RowRenderData(null == cellData ? null : Arrays.asList(cellData), null);
  }

  public static RowRenderData build(PictureRenderData... cellPic) {
    return new RowRenderData(null == cellPic ? null : Arrays.asList(cellPic), (String) null);
  }

  public RowRenderData(List rowData, String backgroundColor) {
    this.cells = new ArrayList();
    if (null != rowData) {
      if (TextRenderData.class.isInstance(rowData.get(0))) {
        rowData.forEach(x -> this.cells.add(new CellRenderData((TextRenderData) x)));
      }

      if (PictureRenderData.class.isInstance(rowData.get(0))) {
        rowData.forEach(x -> this.cells.add(new CellRenderData((PictureRenderData) x)));
      }

      if (String.class.isInstance(rowData.get(0))) {
        rowData.forEach(x -> this.cells.add(new CellRenderData(new TextRenderData((String) x))));
      }
    }

    TableStyle style = new TableStyle();
    style.setBackgroundColor(backgroundColor);
    this.rowStyle = style;
  }

  public int size() {
    return null == cells ? 0 : cells.size();
  }

  public List<CellRenderData> getCells() {
    return cells;
  }

  public void setCells(List<CellRenderData> cellDatas) {
    this.cells = cellDatas;
  }

  public TableStyle getRowStyle() {
    return rowStyle;
  }

  public void setRowStyle(TableStyle style) {
    this.rowStyle = style;
  }

}
