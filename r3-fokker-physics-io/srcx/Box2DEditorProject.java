
package com.jfixby.r3.fokker.core.assets;

import java.io.IOException;

import com.jfixby.scarabei.adopted.gdx.io.JsonValue;
import com.jfixby.scarabei.adopted.gdx.json.red.JsonReader;
import com.jfixby.scarabei.api.assets.Names;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.geometry.Geometry;

public class Box2DEditorProject {
	final List<Box2DEditorShape> shapes_list = Collections.newList();;

	public void addShape (final Box2DEditorShape gdx_shape) {
		this.shapes_list.add(gdx_shape);
	}

	public int size () {
		return this.shapes_list.size();
	}

	public Box2DEditorShape getShape (final int i) {
		return this.shapes_list.getElementAt(i);
	}

	public static Box2DEditorProject loadProject (final File file) throws IOException {
		// String data = gdx_location.readString();

		final Box2DEditorProject project = new Box2DEditorProject();
		final String data_string = file.readToString();
		final JsonValue root = new JsonReader().parse(data_string);
		final int i = 0;
		for (JsonValue list_element = root.getChild("rigidBodies"); list_element != null; list_element = list_element.next) {

			final String shape_name = list_element.getString("name");

			JsonValue origin_child = list_element.getChild("origin");
			final double origin_x = origin_child.asFloat();
			origin_child = origin_child.next;
			final double origin_y = origin_child.asFloat();

			final Box2DEditorShape gdx_shape = new Box2DEditorShape();
			gdx_shape.setID(Names.newID(shape_name));

			project.addShape(gdx_shape);
			// Log.d("list_element", list_element);
			readPolygons(origin_x, origin_y, gdx_shape, list_element.getChild("polygons"));
			readCircles(origin_x, origin_y, gdx_shape, list_element.getChild("circles"));
			readShapes(origin_x, origin_y, gdx_shape, list_element.getChild("shapes"));
			// Log.d("gdx_shape", gdx_shape);
		}

		return project;
	}

	private static void readPolygons (final double origin_x, final double origin_y, final Box2DEditorShape gdx_shape,
		JsonValue polygonsElem) {

		for (; polygonsElem != null; polygonsElem = polygonsElem.next) {

			final RedPolyBodyChain chain = new RedPolyBodyChain();

			gdx_shape.addChain(chain);
			// Log.d("next polygon");
			for (int i = 0; i < polygonsElem.size; i++) {
				final JsonValue val = polygonsElem.get(i);
				// Log.d(" val", val);
				final Float2 point = Geometry.newFloat2();
				final double x = val.child().asFloat() - origin_x;
				// val = val.next;
				final double y = val.child().next.asFloat() - origin_y;

				point.setX(x);
				point.setY(-y);

				chain.addVertex(point);

				// Log.d(" addVertex", x, y);
			}
		}

	}

	private static void readShapes (final double origin_x, final double origin_y, final Box2DEditorShape gdx_shape,
		final JsonValue child) {
		// Log.d("readShapes", child);
		// L.d(child);
		// Err.reportError("WTF?");
	}

	private static void readCircles (final double origin_x, final double origin_y, final Box2DEditorShape gdx_shape,
		JsonValue circleElem) {
		for (; circleElem != null; circleElem = circleElem.next) {

			final RedPolyBodyCircle circle = new RedPolyBodyCircle();

			gdx_shape.addCircle(circle);
			// Log.d("next circle", circleElem);
			for (JsonValue val = circleElem.child(); val != null; val = val.next) {
				final double x = val.asFloat() - origin_x;
				val = val.next;
				final double y = val.asFloat() - origin_y;
				val = val.next;
				final double r = val.asFloat();
				circle.setRadius(r);
				circle.setPosition(x, -y);

			}
		}
	}

}
