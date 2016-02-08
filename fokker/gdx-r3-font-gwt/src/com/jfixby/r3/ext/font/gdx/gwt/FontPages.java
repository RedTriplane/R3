package com.jfixby.r3.ext.font.gdx.gwt;

import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.Map;

public class FontPages {
	// float[][] pages;
	final Map<Integer, FontPage> pages = Collections.newMap();
	private int pageCount;

	public FontPages(int pageCount) {
		this.pageCount = pageCount;

		// pages = new float[pageCount][];
	}

	public int size() {
		return pageCount;
	}

	public FontPage getPage(Integer key) {
		return pages.get(key);
	}

	public FontPage set(Integer key, float[] fs) {
		FontPage page = new FontPage(fs);
		pages.put(key, page);
		return page;
	}
}
