
package com.jfixby.r3.scene2d.red;

import java.util.ArrayList;

import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.r3.activity.api.layer.Component;
import com.jfixby.r3.activity.api.raster.Raster;
import com.jfixby.r3.scene2d.io.LayerElement;
import com.jfixby.r3.scene2d.io.ListItem;
import com.jfixby.r3.scene2d.io.MaterialDesingSection;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.names.ID;
import com.jfixby.scarabei.api.names.Names;

public class RedMaterialDesignConstructor {

	private final RedSceneConstructor redSceneConstructor;

	public RedMaterialDesignConstructor (final RedSceneConstructor redSceneConstructor) {
		this.redSceneConstructor = redSceneConstructor;
	}

	Component restoreMaterialDesign (final LayerElement element, final ComponentsFactory components_factory,
		final Settings settings) {

		if (element.material_design_settings.is_drawer) {
			return this.restoreDrawer(element, components_factory, settings);
		}

		if (element.material_design_settings.is_buttons_list) {
			return this.restoreButtonsList(element, components_factory, settings);
		}

// return null;

		Err.reportError("no result");
		return null;
	}

	Component restoreButtonsList (final LayerElement element, final ComponentsFactory components_factory,
		final Settings settings) {

		final MaterialDesignDepartment mdd = components_factory.getMaterialDesignDepartment();

		final ButtonListSpecs mtds = mdd.newButtonListSpecs();
		{
			final ListItem item = element.material_design_settings.list_item;
			mtds.list_item_raster = this.restore(components_factory, item.raster, settings);

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

		final ButtonList list = mdd.newButtonList(mtds);

		return list;

	}

	<T extends Component> T restore (final ComponentsFactory components_factory, final LayerElement raster,
		final Settings settings) {
		return this.redSceneConstructor.restore(components_factory, raster, settings);
	}

	Component restoreDrawer (final LayerElement element, final ComponentsFactory components_factory, final Settings settings) {

		final MaterialDesignDepartment mdd = components_factory.getMaterialDesignDepartment();

		final DrawerSpecs mtds = mdd.newDrawerSpecs();
		{
			mtds.top_bar_height = element.material_design_settings.top_bar.height;
			final Raster raster = this.restore(components_factory, element.material_design_settings.top_bar.background, settings);
			mtds.top_bar_background = raster;
		}

		{
			final Raster raster = this.restore(components_factory, element.material_design_settings.top_bar.left_icon.background,
				settings);
			mtds.left_icon = raster;
		}

		{
			final Raster raster = this.restore(components_factory, element.material_design_settings.background, settings);
			mtds.drawer_background = raster;
		}
		{
			final ID string_namespace = Names.newID(element.material_design_settings.strings.namespace);
		}
		{
			final ArrayList<MaterialDesingSection> sections = element.material_design_settings.sections;

			for (int i = 0; i < sections.size(); i++) {
				final MaterialDesingSection section = sections.get(i);

				final RedScene scene = this.restore(components_factory, section.layer, settings);

				final DrawerSection sec = mtds.newSection();
				sec.layer = scene.getRoot();

				mtds.sections.add(sec);

			}
		}

		final Drawer drawer = mdd.newDrawer(mtds);

		return drawer;

	}

}
