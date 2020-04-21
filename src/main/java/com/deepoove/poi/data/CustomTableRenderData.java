package com.deepoove.poi.data;

import com.deepoove.poi.common.Merge;
import com.deepoove.poi.data.style.TableStyle;
import java.util.List;

public class CustomTableRenderData implements RenderData {

  /**
   * 通用边距的表格宽度：A4(20.99*29.6),页边距为3.17*2.54
   */
  public static final float WIDTH_A4_FULL = 14.65f;
  /**
   * 窄边距的表格宽度：A4(20.99*29.6),页边距为1.27*1.27
   */
  public static final float WIDTH_A4_NARROW_FULL = 18.45f;
  /**
   * 适中边距的表格宽度：A4(20.99*29.6),页边距为1.91*2.54
   */
  public static final float WIDTH_A4_MEDIUM_FULL = 17.17f;
  /**
   * 宽边距的表格宽度：A4(20.99*29.6),页边距为5.08*2.54
   */
  public static final float WIDTH_A4_EXTEND_FULL = 10.83f;

  /**
   * 表格头部数据，可为空
   */
  private List<RowRenderData> headers;
  /**
   * 表格数据
   */
  private List<RowRenderData> rowDatas;
  /**
   * 表格合并位置
   */
  private List<Merge> merges;
  /**
   * 表格数据为空展示的文案
   */
  private String noDatadesc;

  /**
   * 设置表格整体样式：填充色、整个表格在文档中的对齐方式
   */
  private TableStyle style;

  /**
   * 最大宽度为：页面宽度-页边距宽度*2 单位：cm
   */
  private float width;

  /**
   * 基础表格：行数据填充
   *
   * @param datas
   */
  public CustomTableRenderData(List<RowRenderData> datas) {
    this(null, datas);
  }

  /**
   * 基础表格：行数据填充且指定宽度
   *
   * @param datas
   * @param width
   */
  public CustomTableRenderData(List<RowRenderData> datas, float width) {
    this(null, datas, width);
  }

  /**
   * 空数据表格
   *
   * @param headers
   * @param noDatadesc
   */
  public CustomTableRenderData(List<RowRenderData> headers, String noDatadesc) {
    this(headers, null, noDatadesc, WIDTH_A4_FULL);
  }

  public CustomTableRenderData(List<RowRenderData> headers, List<RowRenderData> datas) {
    this(headers, datas, WIDTH_A4_FULL);
  }

  public CustomTableRenderData(List<RowRenderData> headers, List<RowRenderData> datas,
      List<Merge> merges) {
    this(headers, datas, merges, WIDTH_A4_FULL);
  }

  public CustomTableRenderData(List<RowRenderData> headers, List<RowRenderData> datas,
      float width) {
    this(headers, datas, (String) null, width);
  }

  public CustomTableRenderData(List<RowRenderData> headers, List<RowRenderData> datas,
      List<Merge> merges,
      float width) {
    this(headers, datas, merges, null, width);
  }

  /**
   * @param headers    表格头
   * @param datas      表格数据
   * @param noDatadesc 没有数据显示的文案
   * @param width      宽度
   */
  public CustomTableRenderData(List<RowRenderData> headers, List<RowRenderData> datas,
      String noDatadesc,
      float width) {
    this(headers, datas, null, noDatadesc, width);
  }

  public CustomTableRenderData(List<RowRenderData> headers, List<RowRenderData> datas,
      List<Merge> merges,
      String noDatadesc, float width) {
    this.headers = headers;
    this.rowDatas = datas;
    this.merges = merges;
    this.noDatadesc = noDatadesc;
    this.width = width;
  }


  public boolean isSetHeader() {
    return null != headers && headers.size() > 0;
  }

  public boolean isSetBody() {
    return null != rowDatas && rowDatas.size() > 0;
  }

  public boolean isSetMerge() {
    return null != merges && merges.size() > 0;
  }

  public String getNoDatadesc() {
    return noDatadesc;
  }

  public void setNoDatadesc(String noDatadesc) {
    this.noDatadesc = noDatadesc;
  }

  public List<RowRenderData> getHeaders() {
    return headers;
  }

  public void setHeaders(List<RowRenderData> headers) {
    this.headers = headers;
  }

  public List<RowRenderData> getDatas() {
    return rowDatas;
  }

  public void setDatas(List<RowRenderData> datas) {
    this.rowDatas = datas;
  }

  public float getWidth() {
    return width;
  }

  public void setWidth(float width) {
    this.width = width;
  }

  public TableStyle getStyle() {
    return style;
  }

  public void setStyle(TableStyle style) {
    this.style = style;
  }

  public List<Merge> getMerges() {
    return merges;
  }

  public void setMerges(List<Merge> merges) {
    this.merges = merges;
  }
}
