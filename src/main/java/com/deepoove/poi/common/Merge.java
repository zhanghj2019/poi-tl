package com.deepoove.poi.common;

public class Merge {

	public static final String HORIZONAL = "horizonal";
	public static final String VERTICALLY = "vertically";
	private String direction;
	private Integer position;
	private Integer from;
	private Integer to;

	public Merge(String direction, Integer position, Integer from, Integer to) {
		this.direction = direction;
		this.position = position;
		this.from = from;
		this.to = to;
	}

	public String getDirection() {
		return this.direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Integer getPosition() {
		return this.position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getFrom() {
		return this.from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getTo() {
		return this.to;
	}

	public void setTo(Integer to) {
		this.to = to;
	}
}
