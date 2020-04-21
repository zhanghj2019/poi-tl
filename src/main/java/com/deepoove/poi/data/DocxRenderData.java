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

import com.deepoove.poi.util.ByteUtils;
import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * 待合并子文档和数据集合
 *
 * @author Sayi
 * @version 1.3.0
 */
public class DocxRenderData implements RenderData {

  /**
   * stream流无法重用，使用字节数组表示待合并文档
   */
  private transient byte[] mergeBytes;

  @SuppressWarnings("unused")
  private File file;

  /**
   * 渲染待合并文档模板的数据集合，若合并文档不是个模板，可为空
   */
  private List<?> dataModels;
  private Boolean pageBefore;
  private Boolean pageAfter;

  public DocxRenderData(File docx, Boolean pageBefore, Boolean pageAfter) {
    this((File) docx, (List) null, pageBefore, pageAfter);
  }

  public DocxRenderData(File docx, List<?> renderDatas, Boolean pageBefore, Boolean pageAfter) {
    this(ByteUtils.getLocalByteArray(docx), renderDatas, pageBefore, pageAfter);
    this.file = docx;
  }

  public DocxRenderData(InputStream inputStream, Boolean pageBefore, Boolean pageAfter) {
    this((InputStream) inputStream, (List) null, pageBefore, pageAfter);
  }

  public DocxRenderData(InputStream inputStream, List<?> renderDatas, Boolean pageBefore, Boolean pageAfter) {
    this(ByteUtils.toByteArray(inputStream), renderDatas, pageBefore, pageAfter);
  }

  public DocxRenderData(byte[] input, List<?> renderDatas, Boolean pageBefore, Boolean pageAfter) {
    this.dataModels = renderDatas;
    this.mergeBytes = input;
    this.pageBefore = pageBefore;
    this.pageAfter = pageAfter;
  }

  public byte[] getDocx() {
    return this.mergeBytes;
  }

  public List<?> getDataModels() {
    return this.dataModels;
  }

  public void setDataModels(List<?> renderDatas) {
    this.dataModels = renderDatas;
  }

  public void setRenderDatas(List<?> renderDatas) {
    this.dataModels = renderDatas;
  }

  public Boolean getPageBefore() {
    return this.pageBefore;
  }


  public void setPageBefore(Boolean pageBefore) {
    this.pageBefore = pageBefore;
  }

  public void setPageAfter(Boolean pageAfter) {
    this.pageAfter = pageAfter;
  }

  public Boolean getPageAfter() {
    return this.pageAfter;
  }

}
