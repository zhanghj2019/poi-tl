package com.deepoove.poi.policy;

import static com.deepoove.poi.policy.PictureRenderPolicy.Helper.EMU;

import com.deepoove.poi.common.Merge;
import com.deepoove.poi.data.CellRenderData;
import com.deepoove.poi.data.CustomTableRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.data.style.TableStyle;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.util.StyleUtils;
import com.deepoove.poi.util.TableTools;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomTableRenderPolicy extends AbstractRenderPolicy<CustomTableRenderData> {

  @Override
  protected boolean validate(CustomTableRenderData data) {
    return (null != data && (data.isSetBody() || data.isSetHeader()));
  }

  @Override
  public void doRender(RenderContext<CustomTableRenderData> context) {
    Helper.renderCustomTable(context.getRun(), context.getData());
  }

  @Override
  protected void afterRender(RenderContext<CustomTableRenderData> context) {
    clearPlaceholder(context, true);
  }

  public static class Helper {

    private static Logger LOG = LoggerFactory.getLogger(CustomTableRenderPolicy.Helper.class);

    public static void renderCustomTable(XWPFRun run, CustomTableRenderData data) {
      if (!data.isSetBody()) {
        renderNoDataTable(run, data);
      } else {
        renderTable(run, data);
      }

    }

    /**
     * 填充表格一行的数据
     *
     * @param table
     * @param row     第几行
     * @param rowData 行数据：确保行数据的大小不超过表格该行的单元格数量
     */
    public static void renderRow(XWPFTable table, int row, RowRenderData rowData, TableStyle tableStyle) {
      if (null != rowData && rowData.size() > 0) {
        XWPFTableRow tableRow = table.getRow(row);
        Objects.requireNonNull(tableRow, "Row " + row + " do not exist in the table");

        TableStyle rowStyle = rowData.getRowStyle();
        if (rowStyle == null || (rowStyle != null && tableStyle == null)) {
          rowStyle = tableStyle;
        } else {
          rowStyle.copyStyle(tableStyle);
        }

        List<CellRenderData> cellList = rowData.getCells();
        XWPFTableCell cell = null;

        for (int i = 0; i < cellList.size(); ++i) {
          cell = tableRow.getCell(i);
          if (null == cell) {
            LOG.warn("Extra cell data at row {}, but no extra cell: col {}", row, cell);
            break;
          }
          renderCell(cell, cellList.get(i), rowStyle);
        }

      }
    }

    public static void renderCell(XWPFTableCell cell, CellRenderData cellData, TableStyle rowStyle) {
      TableStyle cellStyle = null == cellData.getCellStyle() ? rowStyle : cellData.getCellStyle();
      // 处理单元格样式
      if (null != cellStyle && null != cellStyle.getBackgroundColor()) {
        cell.setColor(cellStyle.getBackgroundColor());
      }
      if (null != cellStyle && null != cellStyle.getVeticallyAlignment()) {
        cell.setVerticalAlignment(cellStyle.getVeticallyAlignment());
      }

      CTTc ctTc = cell.getCTTc();
      CTP ctP = ctTc.sizeOfPArray() == 0 ? ctTc.addNewP() : ctTc.getPArray(0);
      XWPFParagraph par = new XWPFParagraph(ctP, cell);
      StyleUtils.styleTableParagraph(par, cellStyle);
      TextRenderData renderData = cellData.getCellText();
      if (renderData != null) {
        String cellText = renderData.getText();
        if (StringUtils.isBlank(cellText)) {
          return;
        }
        // 处理单元格数据
        if (null != cellStyle && null != cellStyle.getFontSize()) {
          if (null != renderData.getStyle()) {
            renderData.getStyle().setFontSize(cellStyle.getFontSize());
          } else {
            Style style = new Style();
            style.setFontSize(cellStyle.getFontSize());
            renderData.setStyle(style);
          }
        }

        if (null != cellStyle && null != cellStyle.getFontFamily()) {
          if (null != renderData.getStyle()) {
            renderData.getStyle().setFontFamily(cellStyle.getFontFamily());
          } else {
            Style style = new Style();
            style.setFontFamily(cellStyle.getFontFamily());
            renderData.setStyle(style);
          }
        }

        String text = renderData.getText();
        String[] fragment = text.split(TextRenderPolicy.Helper.REGEX_LINE_CHARACTOR, -1);
        if (fragment.length <= 1) {
          com.deepoove.poi.policy.TextRenderPolicy.Helper.renderTextRun(par.createRun(), renderData);
        } else {
          // 单元格内换行的用不同段落来特殊处理
          for (int j = 0; j < fragment.length; ++j) {
            if (0 != j) {
              par = cell.addParagraph();
              StyleUtils.styleTableParagraph(par, cellStyle);
            }

            XWPFRun run = par.createRun();
            StyleUtils.styleRun(run, renderData.getStyle());
            run.setText(fragment[j]);
          }
        }
      }

      PictureRenderData pictureRenderData = cellData.getPictureRenderData();
      if (pictureRenderData != null) {
        try {
          InputStream ins = null == pictureRenderData.getData() ? new FileInputStream(pictureRenderData.getPath())
              : new ByteArrayInputStream(pictureRenderData.getData());
          try {
            par.createRun().addPicture((InputStream) ins,
                com.deepoove.poi.policy.PictureRenderPolicy.Helper.suggestFileType(pictureRenderData.getPath()), "Generated",
                pictureRenderData.getWidth() * EMU, pictureRenderData.getHeight() * EMU);
          } catch (Exception e) {
            e.printStackTrace();
          } finally {
            if (ins != null) {
              ins.close();
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

    }

    public static void renderTable(XWPFRun run, CustomTableRenderData tableData) {
      // 1.计算行和列
      int row = tableData.getDatas().size();
      int col;
      if (tableData.isSetHeader()) {
        row += tableData.getHeaders().size();
        col = getMaxColumFromData(tableData.getHeaders());
      } else {
        col = getMaxColumFromData(tableData.getDatas());
      }

      BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(run);
      // 2.创建表格
      XWPFTable table = bodyContainer.insertNewTable(run, row, col);
      TableTools.initBasicTable(table, col, tableData.getWidth(), tableData.getStyle());
      // 3.渲染数据
      int startRow = 0;

      if (tableData.isSetHeader()) {
        for (RowRenderData rowData : tableData.getHeaders()) {
          renderRow(table, startRow++, rowData, tableData.getStyle());
        }
      }

      for (RowRenderData rowData : tableData.getDatas()) {
        renderRow(table, startRow++, rowData, tableData.getStyle());
      }
      // 调整表格样式
      if (tableData.getStyle() != null && tableData.getStyle().isSetMargin()) {
        table.setCellMargins(tableData.getStyle().getTopMargin(), tableData.getStyle().getRightMargin(),
            tableData.getStyle().getBottomMargin(), tableData.getStyle().getLeftMargin());
      }
      // 合并表格
      if (tableData.isSetMerge()) {
        mergeTable(table, tableData.getMerges());
        List<RowRenderData> datas = new ArrayList();
        if (tableData.getHeaders() != null) {
          datas.addAll(tableData.getHeaders());
        }

        if (tableData.getDatas() != null) {
          datas.addAll(tableData.getDatas());
        }
        // 重新添加图片
        for (int i = 0; i < datas.size(); ++i) {
          int maxCol = 0;
          if (datas.get(i) != null && datas.get(i).getCells() != null) {
            maxCol = datas.get(i).getCells().size();
          }
          for (int j = 0; j < maxCol; ++j) {
            PictureRenderData picture = datas.get(i).getCells().get(j).getPictureRenderData();
            if (picture != null) {
              try {
                InputStream ins = null == picture.getData() ? new FileInputStream(picture.getPath())
                    : new ByteArrayInputStream(picture.getData());
                XWPFTableCell cell = table.getRow(i).getCell(countPictureCol(tableData.getMerges(), j, maxCol));

                cell.removeParagraph(0);
                XWPFParagraph paragraph = cell.addParagraph();
                paragraph.createRun()
                    .addPicture(ins, com.deepoove.poi.policy.PictureRenderPolicy.Helper.suggestFileType(picture.getPath()), "Generated",
                        picture.getWidth() * EMU, picture.getHeight() * EMU);
                if (null != tableData.getStyle() && null != tableData.getStyle().getBackgroundColor()) {
                  cell.setColor(tableData.getStyle().getBackgroundColor());
                }

                if (null != tableData.getStyle() && null != tableData.getStyle().getVeticallyAlignment()) {
                  cell.setVerticalAlignment(tableData.getStyle().getVeticallyAlignment());
                }
                if (null != tableData.getStyle() && null != tableData.getStyle().getHorizonalAlignment()) {
                  paragraph.setAlignment(tableData.getStyle().getHorizonalAlignment());
                }
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
          }
        }
      }

    }

    public static void renderNoDataTable(XWPFRun run, CustomTableRenderData tableData) {
      int row = tableData.getHeaders().size() + 1;
      int col = getMaxColumFromData(tableData.getHeaders());

      BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(run);
      XWPFTable table = bodyContainer.insertNewTable(run, row, col);
      TableTools.initBasicTable(table, col, tableData.getWidth(), tableData.getStyle());
      int startRow = 0;

      for (RowRenderData head : tableData.getHeaders()) {
        renderRow(table, startRow++, head, tableData.getStyle());
      }

      TableTools.mergeCellsHorizonal(table, row - 1, 0, col - 1);
      XWPFTableCell cell = table.getRow(row - 1).getCell(0);
      cell.setText(tableData.getNoDatadesc());
      if (tableData.isSetMerge()) {
        mergeTable(table, tableData.getMerges());
      }

    }

    private static int getMaxColumFromData(List<RowRenderData> datas) {
      int maxColom = 0;

      for (RowRenderData rowData : datas) {
        if (null != rowData && rowData.size() > maxColom) {
          maxColom = rowData.size();
        }
      }

      return maxColom;
    }

    private static void mergeTable(XWPFTable table, List<Merge> merges) {

      for (Merge merge : merges) {
        if (Merge.HORIZONAL.equals(merge.getDirection())) {
          TableTools.mergeCellsHorizonal(table, merge.getPosition(), merge.getFrom(), merge.getTo());
        } else {
          TableTools.mergeCellsVertically(table, merge.getPosition(), merge.getFrom(), merge.getTo());
        }
      }

    }

    private static int countPictureCol(List<Merge> merges, int col, int maxCol) {
      int flag = maxCol;

      for (Merge merge : merges) {
        if (merge.getFrom() <= col && merge.getTo() >= col && flag > merge.getFrom()) {
          flag = merge.getFrom();
        }
      }

      if (flag < maxCol) {
        return flag;
      } else {
        return 0;
      }
    }
  }
}
