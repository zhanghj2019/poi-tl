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
package com.deepoove.poi.data.style;

import java.math.BigInteger;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;

/**
 * 表格样式
 *
 * @author Sayi
 */
public class TableStyle {

  /**
   * 背景色
   */
  private String backgroundColor;

  /**
   * 对齐方式 STJc.LEFT 左对齐 STJc.CENTER 居中对齐 STJc.RIGHT 右对齐
   */
  private STJc.Enum align;

  /**
   * 单元格文本水平对齐方式
   */
  private ParagraphAlignment horizonalAlignment;
  /**
   * 单元格垂直对齐方式
   */
  private XWPFVertAlign veticallyAlignment;

  /**
   * 表格内行首缩进，固定字符数
   */
  private BigInteger indentation;
  /**
   * 字体
   */
  private String fontFamily;
  /**
   * 字号大小
   */
  private Integer fontSize;
  /**
   * 行距，固定磅值
   */
  private BigInteger spacing;
  /**
   * 表格内容与边框间距
   */
  private Integer rightMargin;
  private Integer leftMargin;
  private Integer bottomMargin;
  private Integer topMargin;

  public String getBackgroundColor() {
    return backgroundColor;
  }

  public void setBackgroundColor(String backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public STJc.Enum getAlign() {
    return align;
  }

  public void setAlign(STJc.Enum align) {
    this.align = align;
  }

  public BigInteger getIndentation() {
    return indentation;
  }

  public void setIndentation(BigInteger indentation) {
    this.indentation = indentation;
  }

  public Integer getFontSize() {
    return fontSize;
  }

  public void setFontSize(Integer fontSize) {
    this.fontSize = fontSize;
  }

  public String getFontFamily() {
    return fontFamily;
  }

  public void setFontFamily(String fontFamily) {
    this.fontFamily = fontFamily;
  }

  public BigInteger getSpacing() {
    return spacing;
  }

  public void setSpacing(BigInteger spacing) {
    this.spacing = spacing;
  }

  public Integer getRightMargin() {
    return rightMargin;
  }

  public Integer getLeftMargin() {
    return leftMargin;
  }

  public Integer getBottomMargin() {
    return bottomMargin;
  }

  public Integer getTopMargin() {
    return topMargin;
  }

  public void setMargin(Integer top, Integer right, Integer bottom, Integer left) {
    this.topMargin = top;
    this.rightMargin = right;
    this.bottomMargin = bottom;
    this.leftMargin = left;
  }

  public boolean isSetMargin() {
    if (this.topMargin != null && this.rightMargin != null && this.bottomMargin != null
        && this.leftMargin != null) {
      return true;
    }
    return false;
  }

  public ParagraphAlignment getHorizonalAlignment() {
    return horizonalAlignment;
  }

  public void setHorizonalAlignment(ParagraphAlignment horizonalAlignment) {
    this.horizonalAlignment = horizonalAlignment;
  }

  public XWPFVertAlign getVeticallyAlignment() {
    return veticallyAlignment;
  }

  public void setVeticallyAlignment(XWPFVertAlign veticallyAlignment) {
    this.veticallyAlignment = veticallyAlignment;
  }

  public void copyStyle(TableStyle overall) {
    if (overall.getVeticallyAlignment() != null && this.getVeticallyAlignment() == null) {
      this.setVeticallyAlignment(overall.getVeticallyAlignment());
    }

    if (overall.getHorizonalAlignment() != null && this.getHorizonalAlignment() == null) {
      this.setHorizonalAlignment(overall.getHorizonalAlignment());
    }

    if (overall.getSpacing() != null && this.getSpacing() == null) {
      this.setSpacing(overall.getSpacing());
    }

    if (overall.getFontFamily() != null && this.getFontFamily() == null) {
      this.setFontFamily(overall.getFontFamily());
    }

    if (overall.getFontSize() != null && this.getFontSize() == null) {
      this.setFontSize(overall.getFontSize());
    }

    if (overall.getIndentation() != null && this.getIndentation() == null) {
      this.setIndentation(overall.getIndentation());
    }

    if (overall.getAlign() != null && this.getAlign() == null) {
      this.setAlign(this.getAlign());
    }

    if (overall.isSetMargin() && !this.isSetMargin()) {
      this.setMargin(overall.getTopMargin(), overall.getRightMargin(), overall.getBottomMargin(), overall.getLeftMargin());
    }

  }

}
