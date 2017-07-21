
package com.jfixby.r3.activity.red.raster;

import com.jfixby.r3.activity.api.raster.Raster;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.math.Angle;
import com.jfixby.scarabei.api.math.FloatMath;

public class RedTileInfo {

	public Raster raster;
	public double top_left_x;
	public double top_left_y;
	public double bottom_right_x;
	public double bottom_right_y;

	final Float2 tmpA = Geometry.newFloat2();
	final Float2 tmpB = Geometry.newFloat2();
	final Float2 tmpC = Geometry.newFloat2();
	final Float2 tmpD = Geometry.newFloat2();

	@Override
	public String toString () {
		return "RedTileInfo [top_left_x=" + top_left_x + ", top_left_y=" + top_left_y + ", bottom_left_x=" + bottom_right_x
			+ ", bottom_left_y=" + bottom_right_y + ", raster=" + raster + "]";
	}

	final RedTileInfo tile = this;
// boolean need_update = !false;

	final void update (final Rectangle shape, final Angle angle) {
// if (!need_update) {
// return;
// }
		final Raster raster = tile.raster;
		final Rectangle tile_shape = raster.shape();

		this.tmpA.setXY(tile.top_left_x, tile.top_left_y);
		this.tmpB.setXY(tile.bottom_right_x, tile.top_left_y);
		this.tmpC.setXY(tile.bottom_right_x, tile.bottom_right_y);
		this.tmpD.setXY(tile.top_left_x, tile.bottom_right_y);

		shape.toAbsolute(this.tmpA);
		shape.toAbsolute(this.tmpB);
		// shape.toAbsolute(tmpC);
		shape.toAbsolute(this.tmpD);

		final double distance_AB = FloatMath.component().distance(this.tmpA.getX(), this.tmpA.getY(), this.tmpB.getX(),
			this.tmpB.getY());
		final double distance_AD = FloatMath.component().distance(this.tmpA.getX(), this.tmpA.getY(), this.tmpD.getX(),
			this.tmpD.getY());
		tile_shape.setOriginRelative(0, 0);
		// tile_shape.setOriginRelative(0, 0);
		// tile_shape.getPosition().getX();
		tile_shape.setWidth(distance_AB);
		tile_shape.setHeight(distance_AD);
		tile_shape.setPosition(tmpA);
		tile_shape.setRotation(angle);
	}

}
