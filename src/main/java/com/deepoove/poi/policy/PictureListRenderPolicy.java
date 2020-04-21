//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.deepoove.poi.policy;

import com.deepoove.poi.data.PictureListRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;
import java.util.Iterator;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class PictureListRenderPolicy extends AbstractRenderPolicy<PictureListRenderData> {

  public PictureListRenderPolicy() {
  }

  protected boolean validate(PictureListRenderData data) {
    return null != data && null != data.getPictureList();
  }

  public void doRender(RenderContext<PictureListRenderData> context) {
    PictureListRenderPolicy.Helper.renderPictureList(context.getRun(), context.getData());
  }

  protected void afterRender(RenderContext<PictureListRenderData> context) {
    clearPlaceholder(context, false);
  }

  protected void reThrowException(RenderContext<PictureListRenderData> context, Exception e) {
    this.logger.info("Render picture " + context.getEleTemplate() + " error: {}", e.getMessage());
  }

  public static class Helper {

    public Helper() {
    }

    public static void renderPictureList(XWPFRun run, PictureListRenderData datas) {
      BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(run);
      Iterator var3 = datas.getPictureList().iterator();

      while (var3.hasNext()) {
        PictureRenderData data = (PictureRenderData) var3.next();
        XWPFParagraph paragraph = bodyContainer.insertNewParagraph(run);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun tempRun = paragraph.createRun();

        try {
          com.deepoove.poi.policy.PictureRenderPolicy.Helper.renderPicture(tempRun, data);
        } catch (Exception var8) {
          var8.printStackTrace();
        }
      }

    }
  }
}
