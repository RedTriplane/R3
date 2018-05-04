
package com.jfixby.r3.activity.red.mdesign;

import java.util.ArrayList;

import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.r3.activity.api.raster.Raster;
import com.jfixby.r3.material.api.DrawerSpecs;
import com.jfixby.r3.material.api.MaterialDesign;
import com.jfixby.r3.material.api.MaterialDesignComponent;
import com.jfixby.r3.material.api.btn.ButtonList;
import com.jfixby.r3.material.api.btn.ButtonListSpecs;
import com.jfixby.r3.material.api.btn.Drawer;
import com.jfixby.r3.material.api.btn.DrawerSection;
import com.jfixby.r3.material.api.btn.Widget;
import com.jfixby.r3.scene2d.io.LayerElement;
import com.jfixby.r3.scene2d.io.ListItem;
import com.jfixby.r3.scene2d.io.MaterialDesingSection;
import com.jfixby.r3.scene2d.red.RedScene;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.names.ID;
import com.jfixby.scarabei.api.names.Names;

public class RedMaterialDesignConstructor implements MaterialDesignComponent {

	@Override
	public DrawerSpecs newDrawerSpecs () {
		return new DrawerSpecs();
	}

	@Override
	public ButtonListSpecs newButtonListSpecs () {
		return new ButtonListSpecs();
	}

	@Override
	public Drawer newDrawer (final DrawerSpecs mtds) {
		return new RedDrawer(mtds);
	}

	@Override
	public ButtonList newButtonList (final ButtonListSpecs mtds) {
		return new RedButtonList(mtds);
	}

	Widget restoreMaterialDesign (final LayerElement element, final ComponentsFactory components_factory) {

		if (element.material_design_settings.is_drawer) {
			return this.restoreDrawer(element, components_factory);
		}

		if (element.material_design_settings.is_buttons_list) {
			return this.restoreButtonsList(element, components_factory);
		}

// return null;

		Err.reportError("no result");
		return null;
	}

	Widget restoreButtonsList (final LayerElement element, final ComponentsFactory components_factory) {

		final ButtonListSpecs mtds = MaterialDesign.newButtonListSpecs();
		{
			final ListItem item = element.material_design_settings.list_item;
			mtds.list_item_raster = this.restore(components_factory, item.raster);

			mtds.list_item_offset_x = item.offset_x;
			mtds.list_item_offset_y = item.offset_y;
			mtds.list_item_width = item.item_width;
			mtds.list_item_height = item.item_height;
		}
		{
			final ListItem item = element.material_design_settings.new_list_item;

			mtds.new_item_offset_x = item.offset_x;
			mtds.new_item_offset_y = item.offset_y;
			mtds.new_item_width = item.item_width;
			mtds.new_item_height = item.item_height;

		}

		final ButtonList list = MaterialDesign.newButtonList(mtds);

		return list;

	}

	<T extends Widget> T restore (final ComponentsFactory components_factory, final LayerElement raster) {
		return this.redSceneConstructor.restore(components_factory, raster);
	}

	Widget restoreDrawer (final LayerElement element, final ComponentsFactory components_factory) {

		final DrawerSpecs mtds = MaterialDesign.newDrawerSpecs();
		{
			mtds.top_bar_height = element.material_design_settings.top_bar.height;
			final Raster raster = this.restore(components_factory, element.material_design_settings.top_bar.background);
			mtds.top_bar_background = raster;
		}

		{
			final Raster raster = this.restore(components_factory, element.material_design_settings.top_bar.left_icon.background);
			mtds.left_icon = raster;
		}

		{
			final Raster raster = this.restore(components_factory, element.material_design_settings.background);
			mtds.drawer_background = raster;
		}
		{
			final ID string_namespace = Names.newID(element.material_design_settings.strings.namespace);
		}
		{
			final ArrayList<MaterialDesingSection> sections = element.material_design_settings.sections;

			for (int i = 0; i < sections.size(); i++) {
				final MaterialDesingSection section = sections.get(i);

				final RedScene scene = this.restore(components_factory, section.layer);

				final DrawerSection sec = mtds.newSection();
				sec.layer = scene.getRoot();

				mtds.sections.add(sec);

			}
		}

		final Drawer drawer = mdd.newDrawer(mtds);

		return drawer;

	}

}
