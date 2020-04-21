package com.deepoove.poi.data;

import java.util.List;

public class PictureListRenderData implements RenderData {

	private List<PictureRenderData> pictureList;

	public List<PictureRenderData> getPictureList() {
		return this.pictureList;
	}

	public void setPictureList(List<PictureRenderData> pictureList) {
		this.pictureList = pictureList;
	}

	public PictureListRenderData(List<PictureRenderData> pictureList) {
		this.pictureList = pictureList;
	}
}
