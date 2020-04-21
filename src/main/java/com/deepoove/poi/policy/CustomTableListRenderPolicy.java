//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.deepoove.poi.policy;

import com.deepoove.poi.data.CustomTableListRenderData;
import com.deepoove.poi.data.CustomTableRenderData;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomTableListRenderPolicy extends AbstractRenderPolicy<CustomTableListRenderData> {

  @Override
  public void doRender(RenderContext<CustomTableListRenderData> context) {
    Helper.renderCustomTableList(context.getRun(), context.getData());
  }

  @Override
  protected void afterRender(RenderContext<CustomTableListRenderData> context) {
    clearPlaceholder(context, true);
  }

  public static class Helper {

    private static Logger LOG = LoggerFactory.getLogger(com.deepoove.poi.policy.CustomTableRenderPolicy.Helper.class);

    public static void renderCustomTableList(XWPFRun run, CustomTableListRenderData datas) {
      BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(run);

      for (CustomTableRenderData data : datas.getTableList()) {
        XWPFParagraph paragraph = bodyContainer.insertNewParagraph(run);
        XWPFRun tempRun = paragraph.createRun();
        if (!data.isSetBody()) {
          com.deepoove.poi.policy.CustomTableRenderPolicy.Helper.renderNoDataTable(tempRun, data);
        } else {
          com.deepoove.poi.policy.CustomTableRenderPolicy.Helper.renderTable(tempRun, data);
        }
      }
    }
  }
}
